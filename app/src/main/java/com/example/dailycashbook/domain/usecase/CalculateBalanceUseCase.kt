package com.example.dailycashbook.domain.usecase

import com.example.dailycashbook.domain.model.Transaction
import com.example.dailycashbook.domain.model.TransactionType
import javax.inject.Inject

data class BalanceResult(
    val totalIn: Double,
    val totalOut: Double,
    val balance: Double
)

class CalculateBalanceUseCase @Inject constructor() {
    operator fun invoke(transactions: List<Transaction>): BalanceResult {
        var totalIn = 0.0
        var totalOut = 0.0
        
        transactions.forEach { transaction ->
            if (transaction.transactionType == TransactionType.IN) {
                totalIn += transaction.amount
            } else {
                totalOut += transaction.amount
            }
        }
        
        return BalanceResult(
            totalIn = totalIn,
            totalOut = totalOut,
            balance = totalIn - totalOut
        )
    }
}
