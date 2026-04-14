package com.example.dailycashbook.domain.model

data class Transaction(
    val id: Long = 0,
    val amount: Double,
    val transactionType: TransactionType,
    val timestamp: Long,
    val description: String
)
