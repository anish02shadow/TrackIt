package com.example69.projectx.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.Date

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "type") val type: String, // "income" or "expense"
    @ColumnInfo(name = "date") val date: LocalDate, // Unix timestamp in milliseconds
    @ColumnInfo(name = "time") val time: LocalTime,
    @ColumnInfo(name = "description") val description: String

)