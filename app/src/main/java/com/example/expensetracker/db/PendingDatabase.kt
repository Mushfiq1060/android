package com.example.expensetracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensetracker.model.Data


@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class PendingDatabase: RoomDatabase() {
    abstract fun pendingDao(): PendingDao
    companion object {
        @Volatile
        private var INSTANCE: PendingDatabase? = null
        fun getPendingDatabase(context: Context): PendingDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PendingDatabase::class.java,
                    "pending_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}