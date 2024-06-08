package com.example.quotes.repository

import com.example.common.Result
import com.example.network.handleApiCall
import com.example.quotes.model.Quote
import com.example.quotes.remotedatasource.QuoteService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(private val quoteService: QuoteService) :
    QuoteRepository {

    override suspend fun getQuotes(): Flow<Result<List<Quote>>> = handleApiCall {
        quoteService.fetchQuotes()
    }
}