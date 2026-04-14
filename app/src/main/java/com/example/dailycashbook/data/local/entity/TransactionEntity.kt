package com.example.dailycashbook.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    indices = [Index(value = ["timestamp"])]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val transactionType: String, // Stored as String ("IN" or "OUT")
    val timestamp: Long,
    val description: String
)
