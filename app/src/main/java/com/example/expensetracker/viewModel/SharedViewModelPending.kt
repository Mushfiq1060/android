package com.example.expensetracker.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.expensetracker.db.PendingDatabase
import com.example.expensetracker.db.RecurrentDatabase
import com.example.expensetracker.model.Data
import com.example.expensetracker.repository.DataStoreRepository
import com.example.expensetracker.repository.PendingRepository
import com.example.expensetracker.repository.RecurrentRepository
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class SharedViewModelPending(application: Application): AndroidViewModel(application){
    private val _factsLiveData = MutableLiveData<Data>()
    val factsLiveData: LiveData<Data>
        get() = _factsLiveData
    val allData: LiveData<List<Data>>
    val allRecurrent: LiveData<List<String>>
    private val repository: PendingRepository
    private val recurRepository: RecurrentRepository
    private var change = false
    init {
        val daoPen = PendingDatabase.getPendingDatabase(application).pendingDao()
        repository = PendingRepository(daoPen)
        allData = repository.getAll()
        val daoRecurrent = RecurrentDatabase.getRecurrentDatabase(application).recurrentDao()
        recurRepository = RecurrentRepository(daoRecurrent)
        allRecurrent = recurRepository.getAll()
    }
    fun update(obj: Data) {
        _factsLiveData.value = obj
    }
    suspend fun insert(data: Data) {
        withContext(Dispatchers.IO) {
            repository.insert(data)
            if(data.type == "Recurrent") {
                recurRepository.insert(data)
            }
        }
    }
    fun delete(data: Data) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(data)
    }
    /*Preference Data Store */
    private val dataStoreRepository = DataStoreRepository(application)
    val readFromDataStore = dataStoreRepository.readFromDataStore.asLiveData()
    fun saveToDataStore(month: String) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveToDatastore(month)
    }
    private suspend fun insertRecurrent(objTitle: String, previousMonth: Int) {
        val calendar = Calendar.getInstance()
        withContext(Dispatchers.IO) {
            while(calendar.get(Calendar.MONTH) >= previousMonth) {
                var objDesc = ""
                var objAmount = ""
                withContext(Dispatchers.IO) {
                    objDesc = recurRepository.findDescription(objTitle)
                    objAmount = recurRepository.findAmount(objTitle)
                }
                val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                val time = sdf.format(calendar.time)
                val obj = Data(null, objTitle, objDesc, time, objAmount, "Recurrent")
                insert(obj)
                calendar.add(Calendar.MONTH, -1)
                change = true
            }
        }
    }
    suspend fun autoUpdateRecurrent(previousMonth: Int, list: List<String>) {
        if(!change) {
            list.forEach { objTitle ->
                withContext(Dispatchers.IO) {
                    insertRecurrent(objTitle, previousMonth)
                }
            }
        }
    }
}