package com.example69.projectx.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transaction_table WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getTransactionsInRange(startDate: Long, endDate: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transaction_table")
    fun getTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transaction_table WHERE id=:id")
    fun getTransaction(id: Int): Flow<Transaction>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)
}
