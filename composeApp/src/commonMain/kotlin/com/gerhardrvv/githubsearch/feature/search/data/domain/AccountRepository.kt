package com.gerhardrvv.githubsearch.feature.search.data.domain

import com.gerhardrvv.githubsearch.core.domain.DataError
import com.gerhardrvv.githubsearch.core.domain.Result

interface AccountRepository {
    suspend fun searchAccounts(
        query: String,
        resultLimit: Int? = null
    ): Result<List<Account>, DataError.Remote>
}