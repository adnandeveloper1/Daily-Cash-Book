package com.example.dailycashbook.presentation.ui.feature.addtransaction

import com.example.dailycashbook.domain.model.TransactionType

data class AddTransactionState(
    val transactionType: TransactionType = TransactionType.IN,
    val amount: String = "",
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isSaved: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
