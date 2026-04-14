package com.example.dailycashbook.core.di

import com.example.dailycashbook.domain.repository.TransactionRepository
import com.example.dailycashbook.domain.usecase.CalculateBalanceUseCase
import com.example.dailycashbook.domain.usecase.DeleteTransactionUseCase
import com.example.dailycashbook.domain.usecase.InsertTransactionUseCase
import com.example.dailycashbook.domain.usecase.ObserveTransactionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideInsertTransactionUseCase(repository: TransactionRepository): InsertTransactionUseCase {
        return InsertTransactionUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideObserveTransactionsUseCase(repository: TransactionRepository): ObserveTransactionsUseCase {
        return ObserveTransactionsUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteTransactionUseCase(repository: TransactionRepository): DeleteTransactionUseCase {
        return DeleteTransactionUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideCalculateBalanceUseCase(): CalculateBalanceUseCase {
        return CalculateBalanceUseCase()
    }
}
