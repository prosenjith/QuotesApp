package com.example.quotes.di

import com.example.quotes.remotedatasource.QuoteService
import com.example.quotes.repository.QuoteRepository
import com.example.quotes.repository.QuoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class QuotesModule {

    companion object{
        @Singleton
        @Provides
        fun provideQuoteService(retrofit: Retrofit): QuoteService {
            return retrofit.create(QuoteService::class.java)
        }
    }

    @Binds
    abstract fun bindQuoteRepository(quoteRepositoryImpl: QuoteRepositoryImpl): QuoteRepository
}