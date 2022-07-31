package com.example.expensetracker.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.expensetracker.db.PendingDao
import com.example.expensetracker.db.PendingDatabase
import com.example.expensetracker.model.Data

class PendingRepository(private val dao: PendingDao) {

    fun getAll(): LiveData<List<Data>> = dao.getAll()

    fun insert(data: Data) = dao.insert(data)

    fun delete(data: Data) = dao.delete(data)

}