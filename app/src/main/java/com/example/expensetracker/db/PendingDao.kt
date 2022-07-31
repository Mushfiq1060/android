package com.example.expensetracker.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensetracker.model.Data


@Dao
interface PendingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: Data)

    @Delete
    fun delete(data: Data)

    @Query("SELECT * FROM data_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Data>>

}