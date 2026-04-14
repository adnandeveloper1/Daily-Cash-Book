package com.example.dailycashbook.domain.usecase

import com.example.dailycashbook.domain.model.Transaction
import com.example.dailycashbook.domain.repository.TransactionRepository
import javax.inject.Inject

class InsertTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        require(transaction.amount > 0) { "Amount must be strictly positive" }
        require(transaction.description.isNotBlank()) { "Description cannot be empty" }
        repository.insertTransaction(transaction)
    }
}
