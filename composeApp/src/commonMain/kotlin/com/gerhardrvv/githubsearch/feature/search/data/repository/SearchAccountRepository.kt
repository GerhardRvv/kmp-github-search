package com.gerhardrvv.githubsearch.feature.search.data.repository

import com.gerhardrvv.githubsearch.core.domain.DataError
import com.gerhardrvv.githubsearch.core.domain.Result
import com.gerhardrvv.githubsearch.feature.search.data.domain.Account
import com.gerhardrvv.githubsearch.feature.search.data.domain.AccountRepository
import com.gerhardrvv.githubsearch.feature.search.data.mapper.toAccount
import com.gerhardrvv.githubsearch.feature.search.data.remote.KtorSearchAccountApi

class DefaultAccountRepository(
    private val searchAccountApi: KtorSearchAccountApi,
): AccountRepository {

    override suspend fun searchAccounts(
        query: String,
        resultLimit: Int?
    ): Result<List<Account>, DataError.Remote> {

        val remoteResult = searchAccountApi.searchAccounts(
            query = query,
            resultLimit = resultLimit
        )

        return when (remoteResult) {
            is Result.Success -> {
                val accounts = remoteResult.data.data.search.edges.map {
                    it.node.toAccount()
                }
                Result.Success(accounts)
            }

            is Result.Error -> {
                Result.Error(remoteResult.error)
            }
        }
    }
}
