package com.example.dailycashbook.presentation.ui.feature.addtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailycashbook.domain.model.Transaction
import com.example.dailycashbook.domain.usecase.InsertTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val insertTransactionUseCase: InsertTransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddTransactionState())
    val state: StateFlow<AddTransactionState> = _state.asStateFlow()

    fun onEvent(event: AddTransactionEvent) {
        when (event) {
            is AddTransactionEvent.OnAmountChange -> {
                _state.update { it.copy(amount = event.amount, isError = false, errorMessage = null) }
            }
            is AddTransactionEvent.OnNotesChange -> {
                _state.update { it.copy(notes = event.notes) }
            }
            is AddTransactionEvent.OnTransactionTypeChange -> {
                _state.update { it.copy(transactionType = event.type) }
            }
            is AddTransactionEvent.OnTimestampChange -> {
                _state.update { it.copy(timestamp = event.timestamp) }
            }
            AddTransactionEvent.OnSaveAndContinue -> {
                saveTransaction { _state.update { AddTransactionState(transactionType = _state.value.transactionType) } }
            }
            AddTransactionEvent.OnSaveAndExit -> {
                saveTransaction { _state.update { it.copy(isSaved = true) } }
            }
            AddTransactionEvent.OnResetState -> {
                _state.update { AddTransactionState(transactionType = it.transactionType) }
            }
        }
    }

    private fun saveTransaction(onSuccess: () -> Unit) {
        val amountValue = _state.value.amount.toDoubleOrNull()
        if (amountValue == null || amountValue <= 0) {
            _state.update { it.copy(isError = true, errorMessage = "Please enter a valid amount") }
            return
        }

        val transaction = Transaction(
            amount = amountValue,
            transactionType = _state.value.transactionType,
            timestamp = _state.value.timestamp,
            description = _state.value.notes
        )

        viewModelScope.launch {
            try {
                insertTransactionUseCase(transaction)
                onSuccess()
            } catch (e: Exception) {
                _state.update { it.copy(isError = true, errorMessage = "Failed to save transaction") }
            }
        }
    }
}
