package com.example.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.quotes.mapper.QuoteMapper
import com.example.quotes.model.QuoteUiState
import com.example.quotes.repository.QuoteRepository
import com.example.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository,
    private val quoteMapper: QuoteMapper
) : ViewModel() {
    private val _quotesUiState = MutableStateFlow<UiState<List<QuoteUiState>>>(UiState.Loading)
    val quotesUiState: StateFlow<UiState<List<QuoteUiState>>> = _quotesUiState

    init {
        getQuotes()
    }

    private fun getQuotes() {
        viewModelScope.launch {
            repository.getQuotes()
                .onStart { _quotesUiState.value = UiState.Loading }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _quotesUiState.value = UiState.Success(result.data.map {
                                quoteMapper.fromDataModel(it)
                            })
                        }

                        is Result.Failure -> {
                            _quotesUiState.value = UiState.Failure(result.exception)
                        }
                    }
                }
        }
    }
}