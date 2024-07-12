package com.example.testrawg.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.testrawg.R
import com.example.testrawg.domain.model.ErrorCodes.Http.BAD_GATEWAY
import com.example.testrawg.domain.model.ErrorCodes.Http.INTERNAL_SERVER
import com.example.testrawg.domain.model.ErrorCodes.Http.RESOURCE_NOT_FOUND
import com.example.testrawg.domain.model.ErrorCodes.Http.SERVER_ERROR
import retrofit2.HttpException
import java.io.IOException

sealed interface ErrorType {

    sealed interface Api : ErrorType {
        data object Network : Api
        data class ServiceUnavailable(val code: Int) : Api
        data object NotFound : Api
        data object Server : Api
        data object BadGateway : Api
        data class Unknown(val code: Int) : Api
    }

    data object Unknown : ErrorType
}

fun Throwable.toErrorType(): ErrorType = when (this) {
    is IOException -> ErrorType.Api.Network
    is HttpException -> when (val code = code()) {
        INTERNAL_SERVER -> ErrorType.Api.Server
        BAD_GATEWAY -> ErrorType.Api.BadGateway
        in SERVER_ERROR -> ErrorType.Api.ServiceUnavailable(code)
        RESOURCE_NOT_FOUND -> ErrorType.Api.NotFound
        else -> ErrorType.Api.Unknown(code)
    }

    else -> ErrorType.Unknown
}

/**
 * Get localized message based on error code of the error
 */
@Composable
fun ErrorType.getResourceMessage(): String = when (this) {
    ErrorType.Api.BadGateway -> stringResource(id = R.string.bad_gateway_error_message)
    ErrorType.Api.Network -> stringResource(id = R.string.data_corrupted_error_message)
    ErrorType.Api.NotFound -> stringResource(id = R.string.not_found_error_message)
    ErrorType.Api.Server -> stringResource(id = R.string.server_error_message)
    is ErrorType.Api.ServiceUnavailable -> stringResource(
        id = R.string.service_unavailable_error_message, code
    )

    is ErrorType.Api.Unknown -> stringResource(id = R.string.error_with_code_error_message, code)
    ErrorType.Unknown -> stringResource(id = R.string.something_went_wrong_error_message)
}
