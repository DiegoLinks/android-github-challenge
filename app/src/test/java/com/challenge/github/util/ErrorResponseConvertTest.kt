package com.challenge.github.util

import com.challenge.github.core.util.ErrorResponse
import com.challenge.github.core.util.ErrorResponseConvert
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test

class ErrorResponseConvertTest {

    @Test
    fun `convertErrorBody should return ErrorResponse object when given a valid errorBody`() {
        // Arrange
        val errorResponseJson = """{"message": "An error occurred"}""".trimIndent()

        val mediaType = "application/json".toMediaTypeOrNull()
        val responseBody = errorResponseJson.toResponseBody(mediaType)
        val expectedErrorResponse = ErrorResponse(message = "An error occurred")

        // Act
        val result = ErrorResponseConvert.convertErrorBody(responseBody)

        // Assert
        assertEquals(expectedErrorResponse, result)
    }

    @Test
    fun `convertErrorBody should return null when given an invalid errorBody`() {
        // Arrange
        val invalidJson = "Invalid JSON"
        val responseBody = invalidJson.toResponseBody(null)

        // Act
        val result = ErrorResponseConvert.convertErrorBody(responseBody)

        // Assert
        assertEquals(null, result)
    }

    @Test
    fun `convertErrorBody should return null when an exception occurs during JSON parsing`() {
        // Arrange
        val errorResponseJson = ""

        val mediaType = "application/json".toMediaTypeOrNull()
        val responseBody = errorResponseJson.toResponseBody(mediaType)

        // Act
        val result = ErrorResponseConvert.convertErrorBody(responseBody)

        // Assert
        assertEquals(null, result)
    }
}