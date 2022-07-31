package com.example.expensetracker.repository

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.*

const val PREFERENCE_NAME = "my_reference"

class DataStoreRepository(context: Context) {
    private val calendar = Calendar.getInstance()
    private object PreferenceKeys {
        val name =  preferencesKey<String>("month")
    }
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )
    suspend fun saveToDatastore(name: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.name] = name
        }
    }
    val readFromDataStore: Flow<String> = dataStore.data
        .catch { exception ->
            if(exception is IOException) {
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val month = preference[PreferenceKeys.name] ?: calendar.get(Calendar.MONTH).toString()
            month
        }
}