package com.example.ui

sealed class UiState<out T> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Failure(val exception: Throwable) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
}