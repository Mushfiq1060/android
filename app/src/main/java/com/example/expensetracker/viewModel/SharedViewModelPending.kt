package com.example.expensetracker.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.expensetracker.db.ExpenseDatabase
import com.example.expensetracker.db.PendingDatabase
import com.example.expensetracker.model.Data
import com.example.expensetracker.repository.DataStoreRepository
import com.example.expensetracker.repository.ExpenseRepository
import com.example.expensetracker.repository.PendingRepository
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
    private val exRepository: ExpenseRepository
    private var change = false
    init {
        val daoPen = PendingDatabase.getPendingDatabase(application).pendingDao()
        repository = PendingRepository(daoPen)
        allData = repository.getAll()
        val daoEx = ExpenseDatabase.getExpenseDatabase(application).expenseDao()
        exRepository = ExpenseRepository(daoEx)
        allRecurrent = exRepository.getRecurrentAll("Recurrent")
    }
    fun update(obj: Data) {
        _factsLiveData.value = obj
    }
    suspend fun insert(data: Data) {
        withContext(Dispatchers.IO) {
            repository.insert(data)
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
                    objDesc = exRepository.findDescription(objTitle)
                    objAmount = exRepository.findAmount(objTitle)
                }
                val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                val time = sdf.format(calendar.time)
                val obj: Data = Data(null, objTitle, objDesc, time, objAmount, "Recurrent")
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