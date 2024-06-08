package com.example.quotes.remotedatasource

import com.example.quotes.model.Quote
import retrofit2.Response
import retrofit2.http.GET

interface QuoteService {
    @GET("quotes")
    suspend fun fetchQuotes(): Response<List<Quote>>
}