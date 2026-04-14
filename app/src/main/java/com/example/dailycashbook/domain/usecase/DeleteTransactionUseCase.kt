package com.example.dailycashbook.domain.usecase

import com.example.dailycashbook.domain.model.Transaction
import com.example.dailycashbook.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.deleteTransaction(transaction)
    }
}
