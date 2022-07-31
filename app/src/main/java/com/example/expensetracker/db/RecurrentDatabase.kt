package com.example.expensetracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensetracker.model.Data

@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class RecurrentDatabase: RoomDatabase() {
    abstract fun recurrentDao(): RecurrentDao
    companion object {
        @Volatile
        private var INSTANCE: RecurrentDatabase? = null
        fun getRecurrentDatabase(context: Context): RecurrentDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecurrentDatabase::class.java,
                    "recurrent_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}