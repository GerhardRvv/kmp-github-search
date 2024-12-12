package com.gerhardrvv.githubsearch.feature.search.data.remote

import com.gerhardrvv.githubsearch.core.data.safeApiCall
import com.gerhardrvv.githubsearch.core.domain.DataError
import com.gerhardrvv.githubsearch.core.domain.Result
import com.gerhardrvv.githubsearch.core.remote.SearchAccountRemote
import com.gerhardrvv.githubsearch.feature.search.data.remote.query.GraphQLQueries
import io.ktor.client.HttpClient

import io.ktor.client.request.post
import io.ktor.client.request.setBody

class KtorSearchAccountApi(
    private val httpClient: HttpClient
) : SearchAccountApi {

    override suspend fun searchAccounts(
        query: String,
        resultLimit: Int?
    ): Result<SearchAccountRemote, DataError.Remote> {
        return safeApiCall {

            val graphQLQuery = GraphQLQueries.SEARCH_ACCOUNTS.replace("\n", " ")

            val requestBody = """
                {
                    "query": "$graphQLQuery",
                    "variables": {
                        "query": "$query",
                        "resultLimit": ${resultLimit ?: 20}
                    }
                }
            """.trimIndent()

            httpClient.post {
                setBody(requestBody)
            }
        }
    }
}
