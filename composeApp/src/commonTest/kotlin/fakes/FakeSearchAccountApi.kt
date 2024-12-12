package fakes

import com.gerhardrvv.githubsearch.core.domain.DataError
import com.gerhardrvv.githubsearch.core.domain.Result
import com.gerhardrvv.githubsearch.core.remote.SearchAccountRemote
import com.gerhardrvv.githubsearch.feature.search.data.remote.SearchAccountApi

class FakeSearchAccountApi(
    private val response: Result<SearchAccountRemote, DataError.Remote>
) : SearchAccountApi {

    override suspend fun searchAccounts(
        query: String,
        resultLimit: Int?
    ): Result<SearchAccountRemote, DataError.Remote> {
        return response
    }
}
