package com.example.testrawg.common.result

import android.util.Log
import com.example.testrawg.domain.model.ErrorType
import com.example.testrawg.domain.model.toErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val errorType: ErrorType) : Result<Nothing>
    data object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> = map<T, Result<T>> { Result.Success(it) }
    .onStart { emit(Result.Loading) }
    .catch {
        Log.e("AsResult", "Flow caught error", it)
        val errorType = it.toErrorType()
        emit(Result.Error(errorType))
    }
