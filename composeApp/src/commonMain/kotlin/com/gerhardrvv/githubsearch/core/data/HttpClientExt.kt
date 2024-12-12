package com.gerhardrvv.githubsearch.core.data

import com.gerhardrvv.githubsearch.core.domain.DataError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import com.gerhardrvv.githubsearch.core.domain.Result

import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeApiCall(
    execute: () -> HttpResponse
): Result<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        return Result.Error(DataError.Remote.NO_INTERNET_CONNECTION)
    } catch (e: Exception) {
        e.printStackTrace()
        coroutineContext.ensureActive()
        return Result.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Remote> {
    return when(response.status.value) {
        in 200 .. 299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(DataError.Remote.SERIALIZATION_ERROR)
            }
        }
        401 -> Result.Error(DataError.Remote.INVALID_CREDENTIALS)
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500 .. 599 -> Result.Error(DataError.Remote.SERVER_ERROR)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}