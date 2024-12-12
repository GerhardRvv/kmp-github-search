package com.gerhardrvv.githubsearch.core.domain

sealed interface DataError: Error {
    enum class Remote: DataError {
        REQUEST_FAILED,
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET_CONNECTION,
        SERVER_ERROR,
        SERIALIZATION_ERROR,
        INVALID_CREDENTIALS,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }
}
