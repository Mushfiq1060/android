package com.example.expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "data_table")
data class Data(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "Title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "time") var time: String,
    @ColumnInfo(name = "amount") var amount: String,
    @ColumnInfo(name = "type") var type: String
): Serializable
