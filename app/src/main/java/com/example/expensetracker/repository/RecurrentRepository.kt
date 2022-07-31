package com.example.expensetracker.repository

import com.example.expensetracker.db.RecurrentDao
import com.example.expensetracker.model.Data


class RecurrentRepository(private val dao: RecurrentDao) {

    fun getAll() = dao.getAll()

    fun insert(data: Data) = dao.insert(data)

    fun findDescription(query: String) = dao.findDescription(query)

    fun findAmount(query: String) = dao.findAmount(query)
}