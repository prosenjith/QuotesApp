package com.example.quotes.di

import com.example.quotes.model.Quote
import com.example.quotes.mapper.QuoteMapper
import com.example.quotes.model.QuoteUiState
import com.example.ui.ModelMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MapperModule {

    @Binds
    abstract fun bindQuoteMapper(
        quoteMapper: QuoteMapper
    ): ModelMapper<Quote, QuoteUiState>
}