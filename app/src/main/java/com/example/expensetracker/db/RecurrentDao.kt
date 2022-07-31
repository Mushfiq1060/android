package com.example.expensetracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensetracker.model.Data

@Dao
interface RecurrentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: Data)

    @Query("SELECT DISTINCT title FROM data_table")
    fun getAll(): LiveData<List<String>>

    @Query("SELECT description FROM data_table WHERE title LIKE :query")
    fun findDescription(query: String): String

    @Query("SELECT amount FROM data_table WHERE title LIKE :query")
    fun findAmount(query: String): String

}