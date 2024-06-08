package com.example.quotes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quotes.QuoteViewModel
import com.example.quotes.model.QuoteUiState
import com.example.ui.UiState

@Composable
fun QuoteScreen(viewModel: QuoteViewModel = viewModel()) {
    val quotesUiState by viewModel.quotesUiState.collectAsState()

    when (quotesUiState) {
        is UiState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items((quotesUiState as UiState.Success<List<QuoteUiState>>).data.size) {
                    val quote = (quotesUiState as UiState.Success<List<QuoteUiState>>).data[it]
                    QuoteCard(quote = quote.text.orEmpty(), author = quote.author.orEmpty())
                }
            }
        }

        is UiState.Failure -> {
            val exception = (quotesUiState as UiState.Failure).exception
            Text(text = "Error: ${exception.localizedMessage}")
        }

        UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}