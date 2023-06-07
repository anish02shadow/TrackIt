package com.example69.projectx.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getTransactionsInRangeStream(startDate: Long, endDate: Long): Flow<List<Transaction>>

    fun getTransactionsStream(): Flow<List<Transaction>>

    fun getTransactionStream(id:Int) :Flow<Transaction?>


    suspend fun addTransaction(transaction: Transaction)


    suspend fun updateTransaction(transaction: Transaction)


    suspend fun deleteTransaction(transaction: Transaction)
}