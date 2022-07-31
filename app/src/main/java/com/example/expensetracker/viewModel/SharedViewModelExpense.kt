package com.example.expensetracker.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.expensetracker.db.ExpenseDatabase
import com.example.expensetracker.model.Data
import com.example.expensetracker.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModelExpense(application: Application): AndroidViewModel(application) {
    private val _factsLiveData = MutableLiveData<Data>()
    val factsLiveData: LiveData<Data>
        get() = _factsLiveData
    fun update(obj: Data) {
        _factsLiveData.value = obj
    }
    val allData: LiveData<List<Data>>
    private val repository: ExpenseRepository
    init {
        val dao = ExpenseDatabase.getExpenseDatabase((application)).expenseDao()
        repository = ExpenseRepository(dao)
        allData = repository.getAll()
    }
    fun insert(data: Data) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(data)
    }
    fun searchData(query: String): LiveData<List<Data>> {
        return repository.searchData(query)
    }
}