package com.example.quotes.mapper

import com.example.quotes.model.Quote
import com.example.quotes.model.QuoteUiState
import com.example.ui.ModelMapper
import javax.inject.Inject

class QuoteMapper @Inject constructor() : ModelMapper<Quote, QuoteUiState> {
    override fun fromDataModel(dataModel: Quote): QuoteUiState {
        return QuoteUiState(
            text = dataModel.text,
            author = dataModel.author
        )
    }

    override fun toDataModel(uiModel: QuoteUiState): Quote {
        return Quote(
            text = uiModel.text,
            author = uiModel.author
        )
    }
}