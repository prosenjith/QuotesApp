package com.example.quotes.repository

import com.example.common.Result
import com.example.quotes.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    suspend fun getQuotes(): Flow<Result<List<Quote>>>
}