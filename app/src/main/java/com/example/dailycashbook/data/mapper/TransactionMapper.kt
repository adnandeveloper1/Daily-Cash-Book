package com.example.dailycashbook.data.mapper

import com.example.dailycashbook.data.local.entity.TransactionEntity
import com.example.dailycashbook.domain.model.Transaction
import com.example.dailycashbook.domain.model.TransactionType

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        amount = amount,
        transactionType = TransactionType.valueOf(transactionType),
        timestamp = timestamp,
        description = description
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        amount = amount,
        transactionType = transactionType.name,
        timestamp = timestamp,
        description = description
    )
}
