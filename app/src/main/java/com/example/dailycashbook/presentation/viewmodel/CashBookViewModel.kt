package com.example.dailycashbook.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailycashbook.domain.model.FilterType
import com.example.dailycashbook.domain.model.Transaction
import com.example.dailycashbook.domain.usecase.CalculateBalanceUseCase
import com.example.dailycashbook.domain.usecase.DeleteTransactionUseCase
import com.example.dailycashbook.domain.usecase.InsertTransactionUseCase
import com.example.dailycashbook.domain.usecase.ObserveTransactionsUseCase
import com.example.dailycashbook.presentation.state.CashBookUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CashBookViewModel @Inject constructor(
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val calculateBalanceUseCase: CalculateBalanceUseCase
) : ViewModel() {

    private val _currentFilter = MutableStateFlow(FilterType.ALL)

    val uiState: StateFlow<CashBookUiState> = _currentFilter
        .flatMapLatest { filter ->
            observeTransactionsUseCase(filter).map { transactions ->
                val balance = calculateBalanceUseCase(transactions)
                CashBookUiState(
                    isLoading = false,
                    transactions = transactions,
                    balanceResult = balance,
                    currentFilter = filter,
                    error = null
                )
            }
        }.catch { e ->
            emit(CashBookUiState(isLoading = false, error = e.message ?: "Unknown error"))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CashBookUiState(isLoading = true)
        )

    fun changeFilter(filterType: FilterType) {
        _currentFilter.value = filterType
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                insertTransactionUseCase(transaction)
            } catch (e: Exception) {
                // handle error appropriately
            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                deleteTransactionUseCase(transaction)
            } catch (e: Exception) {
                // handle error
            }
        }
    }
}
