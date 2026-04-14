package com.example.dailycashbook.core.di

import com.example.dailycashbook.data.local.dao.TransactionDao
import com.example.dailycashbook.data.repository.TransactionRepositoryImpl
import com.example.dailycashbook.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    // Using @Provides instead of @Binds since we construct the impl here
    @Provides
    @Singleton
    fun provideTransactionRepository(dao: TransactionDao): TransactionRepository {
        return TransactionRepositoryImpl(dao)
    }
}
