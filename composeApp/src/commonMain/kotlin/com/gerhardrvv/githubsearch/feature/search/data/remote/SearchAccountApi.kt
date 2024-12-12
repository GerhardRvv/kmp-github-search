package com.gerhardrvv.githubsearch.feature.search.data.remote

import com.gerhardrvv.githubsearch.core.domain.DataError
import com.gerhardrvv.githubsearch.core.domain.Result
import com.gerhardrvv.githubsearch.core.remote.SearchAccountRemote

interface SearchAccountApi {
    suspend fun searchAccounts(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchAccountRemote, DataError.Remote>
}
