package com.example.expensetracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expensetracker.model.Data

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: Data)

    @Query("SELECT * FROM data_table ORDER BY time DESC")
    fun getAll(): LiveData<List<Data>>

    @Query("SELECT DISTINCT title FROM data_table WHERE type LIKE :query")
    fun getRecurrentAll(query: String): LiveData<List<String>>

    @Query("SELECT * FROM data_table WHERE title LIKE :query || '%' ")
    fun searchData(query: String): LiveData<List<Data>>

    @Query("SELECT description FROM data_table WHERE title LIKE :query")
    fun findDescription(query: String): String

    @Query("SELECT amount FROM data_table WHERE title LIKE :query")
    fun findAmount(query: String): String

    @Query("SELECT * FROM data_table WHERE title LIKE :title AND type LIKE :type")
    fun doesExpenseExist(title: String, type: String): Int

}