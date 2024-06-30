package com.example.quotes

import com.example.common.Result
import com.example.quotes.model.Quote
import com.example.quotes.remotedatasource.QuoteService
import com.example.quotes.repository.QuoteRepository
import com.example.quotes.repository.QuoteRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class QuoteRepositoryImplTest {

    private lateinit var quoteRepository: QuoteRepository
    private val quoteService: QuoteService = mock()

    @BeforeEach
    fun setUp() {
        quoteRepository = QuoteRepositoryImpl(quoteService)
    }

    @Test
    fun `test getQuotes returns quotes successfully`() = runBlocking {
        // Arrange
        val quotes = listOf(Quote("Test Quote 1", "Author 1"), Quote("Test Quote 2", "Author 2"))
        val response = Response.success(quotes)
        whenever(runBlocking { quoteService.fetchQuotes() }).doReturn(response)

        // Act
        val result = quoteRepository.getQuotes().first()

        // Assert
        assert(result is Result.Success)
        assert((result as Result.Success).data == quotes)
    }

    @Test
    fun `test getQuotes returns error`(): Unit = runBlocking {
        // Arrange
        val responseBody = "Internal Server Error"
            .toResponseBody("text/plain".toMediaTypeOrNull()) // Adjust media type if needed
        val response = Response.error<List<Quote>>(500, responseBody)
        whenever(runBlocking { quoteService.fetchQuotes() }).doReturn(response)

        // Act & Assert
        try {
            quoteRepository.getQuotes().first()
        } catch (e: HttpException) {
            // Assert
            assert(e.code() == 500)
            assert(e.message == "Internal Server Error")
        }
    }

    @Test
    fun `test getQuotes throws exception`() = runBlocking {
        // Arrange
        val exception =
            RuntimeException("Network Error") // Using RuntimeException or other unchecked exception
        whenever(runBlocking { quoteService.fetchQuotes() }).doThrow(exception)

        // Act
        val result = quoteRepository.getQuotes().first()

        // Assert
        assert(result is Result.Failure)
        assert((result as Result.Failure).exception.message == "Network Error")
    }
}
