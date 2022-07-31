package com.example.expensetracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensetracker.model.Data

@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null
        fun getExpenseDatabase(context: Context): ExpenseDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}