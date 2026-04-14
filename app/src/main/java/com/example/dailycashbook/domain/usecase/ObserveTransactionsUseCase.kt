package com.example.dailycashbook.domain.usecase

import com.example.dailycashbook.domain.model.FilterType
import com.example.dailycashbook.domain.model.Transaction
import com.example.dailycashbook.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class ObserveTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(filterType: FilterType): Flow<List<Transaction>> {
        if (filterType == FilterType.ALL) {
            return repository.getAllTransactions()
        }

        val calendar = Calendar.getInstance()
        val startTimestamp: Long
        val endTimestamp = System.currentTimeMillis()

        when (filterType) {
            FilterType.DAILY -> {
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                startTimestamp = calendar.timeInMillis
            }
            FilterType.WEEKLY -> {
                calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                startTimestamp = calendar.timeInMillis
            }
            FilterType.MONTHLY -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                startTimestamp = calendar.timeInMillis
            }
            FilterType.YEARLY -> {
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                startTimestamp = calendar.timeInMillis
            }
            else -> {
                return repository.getAllTransactions()
            }
        }
        
        return repository.getTransactionsBetween(startTimestamp, endTimestamp)
    }
}
