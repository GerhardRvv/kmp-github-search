package com.gerhardrvv.githubsearch.feature.search.data.repository

import com.gerhardrvv.githubsearch.core.domain.DataError
import com.gerhardrvv.githubsearch.core.domain.Result
import com.gerhardrvv.githubsearch.feature.search.data.db.AccountDao
import com.gerhardrvv.githubsearch.feature.search.data.domain.Account
import com.gerhardrvv.githubsearch.feature.search.data.domain.AccountRepository
import com.gerhardrvv.githubsearch.feature.search.data.mapper.toAccount
import com.gerhardrvv.githubsearch.feature.search.data.mapper.toAccountEntity
import com.gerhardrvv.githubsearch.feature.search.data.remote.SearchAccountApi

class DefaultAccountRepository(
    private val searchAccountApi: SearchAccountApi,
    private val accountDao: AccountDao
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

                accountDao.deleteAccountsByQuery(query)
                accountDao.insertAccounts(accounts.map { it.toAccountEntity(query) })

                Result.Success(accounts)
            }

            is Result.Error -> {
                val cachedAccounts = accountDao.getAccountsByQuery(query).map { it.toAccount() }
                if (cachedAccounts.isNotEmpty()) {
                    Result.Success(cachedAccounts)
                } else {
                    remoteResult
                }
            }
        }
    }
}
