package com.example.dailycashbook.data.repository

import com.example.dailycashbook.data.local.dao.TransactionDao
import com.example.dailycashbook.data.mapper.toDomain
import com.example.dailycashbook.data.mapper.toEntity
import com.example.dailycashbook.domain.model.Transaction
import com.example.dailycashbook.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTransactionsBetween(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<Transaction>> {
        return dao.getTransactionsBetween(startTimestamp, endTimestamp).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction.toEntity())
    }
}
