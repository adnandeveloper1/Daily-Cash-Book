package com.example.dailycashbook.presentation.state

import com.example.dailycashbook.domain.model.FilterType
import com.example.dailycashbook.domain.model.Transaction
import com.example.dailycashbook.domain.usecase.BalanceResult

data class CashBookUiState(
    val isLoading: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val balanceResult: BalanceResult = BalanceResult(0.0, 0.0, 0.0),
    val currentFilter: FilterType = FilterType.ALL,
    val error: String? = null
)
