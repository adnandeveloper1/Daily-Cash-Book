package com.example.dailycashbook.presentation.ui.feature.addtransaction

import com.example.dailycashbook.domain.model.TransactionType

sealed class AddTransactionEvent {
    data class OnTransactionTypeChange(val type: TransactionType) : AddTransactionEvent()
    data class OnAmountChange(val amount: String) : AddTransactionEvent()
    data class OnNotesChange(val notes: String) : AddTransactionEvent()
    data class OnTimestampChange(val timestamp: Long) : AddTransactionEvent()
    object OnSaveAndExit : AddTransactionEvent()
    object OnSaveAndContinue : AddTransactionEvent()
    object OnResetState : AddTransactionEvent() // Called after Save And Continue
}
