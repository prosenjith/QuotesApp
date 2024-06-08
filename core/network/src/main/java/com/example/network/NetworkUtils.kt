package com.example.network

import com.example.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> Response<T>.toResult(): Result<T> {
    return if (this.isSuccessful) {
        this.body()?.let {
            Result.success(it)
        } ?: Result.failure(Exception("Response body is null"))
    } else {
        Result.failure(Exception("HTTP error ${this.code()}: ${this.message()}"))
    }
}

fun <T> handleApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> Response<T>
): Flow<Result<T>> = flow {
    try {
        val response = withContext(dispatcher) {
            apiCall()
        }
        emit(response.toResult())
    } catch (e: Exception) {
        emit(Result.failure(e))
    }
}.flowOn(dispatcher)