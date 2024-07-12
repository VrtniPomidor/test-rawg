package com.example.testrawg.domain.model

import junit.framework.TestCase.assertEquals
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response


class ErrorTypeKtTest {

    @Test
    fun testMappingErrorTypeToApiUnknown() {
        val expected = ErrorType.Api.Network
        val exception = IOException()

        val actual = exception.toErrorType()
        assertEquals(expected, actual)
    }

    @Test
    fun testMappingErrorTypeToNetwork() {
        val unauthorizedCode = 401
        val expected = ErrorType.Api.Unknown(unauthorizedCode)
        val exception = HttpException(
            Response.error<ResponseBody>(
                unauthorizedCode, // Random code
                "unauthorized".toResponseBody("plain/text".toMediaTypeOrNull())
            )
        )

        val actual = exception.toErrorType()
        assertEquals(expected, actual)
    }

    @Test
    fun testMappingErrorTypeToServer() {
        val expected = ErrorType.Api.Server
        val exception = HttpException(
            Response.error<ResponseBody>(
                ErrorCodes.Http.INTERNAL_SERVER,
                "internal server".toResponseBody("plain/text".toMediaTypeOrNull())
            )
        )

        val actual = exception.toErrorType()
        assertEquals(expected, actual)
    }

    @Test
    fun testMappingErrorTypeToServiceUnavailable() {
        val serverCode = 521
        val expected = ErrorType.Api.ServiceUnavailable(serverCode)
        val exception = HttpException(
            Response.error<ResponseBody>(
                serverCode, // Random code in 500 range
                "random error".toResponseBody("plain/text".toMediaTypeOrNull())
            )
        )

        val actual = exception.toErrorType()
        assertEquals(expected, actual)
    }

    @Test
    fun testMappingErrorTypeToBadGateway() {
        val expected = ErrorType.Api.BadGateway
        val exception = HttpException(
            Response.error<ResponseBody>(
                ErrorCodes.Http.BAD_GATEWAY,
                "bad gateway".toResponseBody("plain/text".toMediaTypeOrNull())
            )
        )

        val actual = exception.toErrorType()
        assertEquals(expected, actual)
    }

    @Test
    fun testMappingErrorTypeToNotFound() {
        val expected = ErrorType.Api.NotFound
        val exception = HttpException(
            Response.error<ResponseBody>(
                ErrorCodes.Http.RESOURCE_NOT_FOUND,
                "not found".toResponseBody("plain/text".toMediaTypeOrNull())
            )
        )

        val actual = exception.toErrorType()
        assertEquals(expected, actual)
    }

    @Test
    fun testMappingErrorTypeToUnknown() {
        val expected = ErrorType.Unknown
        val exception = RuntimeException()

        val actual = exception.toErrorType()
        assertEquals(expected, actual)
    }
}
