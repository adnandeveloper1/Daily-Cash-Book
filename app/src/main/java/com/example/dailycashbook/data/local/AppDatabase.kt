package com.example.dailycashbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dailycashbook.data.local.dao.TransactionDao
import com.example.dailycashbook.data.local.entity.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao
    
    companion object {
        const val DATABASE_NAME = "cash_book_db"
    }
}
