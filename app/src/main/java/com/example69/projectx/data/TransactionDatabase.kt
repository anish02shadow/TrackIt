package com.example69.projectx.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var instance: TransactionDatabase? = null

        fun getDatabase(context: Context): TransactionDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context,
                    TransactionDatabase::class.java,
                    "transaction_database"
                ).build().also {
                    instance = it
                }
            }
        }
    }
}
