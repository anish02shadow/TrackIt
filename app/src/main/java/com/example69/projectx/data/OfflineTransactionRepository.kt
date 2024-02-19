package com.example69.projectx.data

import kotlinx.coroutines.flow.Flow

class OfflineTransactionRepository(private val transactionDao: TransactionDao): TransactionRepository {

    override fun getTransactionsInRangeStream(
        startDate: Long,
        endDate: Long
    ): Flow<List<Transaction>> = transactionDao.getTransactionsInRange(startDate,endDate)

    override fun getTransactionsStream(): Flow<List<Transaction>> = transactionDao.getTransactions()

    override fun getTransactionStream(id: Int): Flow<Transaction?> = transactionDao.getTransaction(id)

    override suspend fun addTransaction(transaction: Transaction)=transactionDao.add(transaction)

    override suspend fun deleteTransaction(transaction: Transaction)=transactionDao.delete(transaction)

    override suspend fun updateTransaction(transaction: Transaction)=transactionDao.update(transaction)

}
