package com.example.expensetracker.repository

import com.example.expensetracker.db.ExpenseDao
import com.example.expensetracker.model.Data

class ExpenseRepository(private val dao: ExpenseDao) {

    fun getAll() = dao.getAll()

    fun insert(data: Data) = dao.insert(data)

    fun getRecurrentAll(query: String) = dao.getRecurrentAll(query)

    fun searchData(query: String) = dao.searchData(query)

    fun findDescription(query: String) = dao.findDescription(query)

    fun findAmount(query: String) = dao.findAmount(query)
}