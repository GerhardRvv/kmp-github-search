package repository

import com.gerhardrvv.githubsearch.core.domain.DataError
import com.gerhardrvv.githubsearch.core.remote.RepositoryInfo
import com.gerhardrvv.githubsearch.core.remote.SearchAccountRemote
import com.gerhardrvv.githubsearch.core.remote.SearchData
import com.gerhardrvv.githubsearch.core.remote.SearchEdge
import com.gerhardrvv.githubsearch.core.remote.SearchNode
import com.gerhardrvv.githubsearch.core.remote.SearchResult
import com.gerhardrvv.githubsearch.feature.search.data.db.AccountEntity
import com.gerhardrvv.githubsearch.core.domain.Result
import com.gerhardrvv.githubsearch.feature.search.data.repository.DefaultAccountRepository
import fakes.FakeAccountDao
import fakes.FakeSearchAccountApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking

class DefaultAccountRepositoryTest {

    private val fakeAccountDao = FakeAccountDao()

    private val successfulRemoteResponse = Result.Success(
        SearchAccountRemote(
            data = SearchData(
                search = SearchResult(
                    edges = listOf(
                        SearchEdge(
                            node = SearchNode(
                                id = "1",
                                login = "test_user",
                                avatarUrl = "",
                                name = null,
                                repositories = RepositoryInfo(totalCount = 0)
                            )
                        )
                    )
                )
            )
        )
    )

    private val accountEntity = AccountEntity(
        id = "1",
        username = "cached_user",
        avatarUrl = "",
        repoCount = 0,
        query = "test"
    )

    @Test
    fun `should return accounts from remote source and cache them`() = runBlocking {
        val remoteResponse = successfulRemoteResponse
        val remoteAccountDataSource = FakeSearchAccountApi(remoteResponse)
        val repository = DefaultAccountRepository(remoteAccountDataSource, fakeAccountDao)

        val result = repository.searchAccounts("test", null)

        assertTrue(result is Result.Success)
        val accounts = result.data
        assertEquals(1, accounts.size)
        assertEquals("test_user", accounts.first().username)

        val cachedAccounts = fakeAccountDao.getAccountsByQuery("test")
        assertEquals(1, cachedAccounts.size)
        assertEquals("test_user", cachedAccounts.first().username)
    }

    @Test
    fun `should return cached accounts when remote source fails`() = runBlocking {
        fakeAccountDao.insertAccounts(
            listOf(accountEntity)
        )
        val remoteResponse = Result.Error(DataError.Remote.UNKNOWN)
        val remoteAccountDataSource = FakeSearchAccountApi(remoteResponse)
        val repository = DefaultAccountRepository(remoteAccountDataSource, fakeAccountDao)

        val result = repository.searchAccounts("test", null)

        assertTrue(result is Result.Success)
        val accounts = result.data
        assertEquals(1, accounts.size)
        assertEquals("cached_user", accounts.first().username)
    }

    @Test
    fun `should return remote error if no cached accounts available`() = runBlocking {
        val remoteResponse = Result.Error(DataError.Remote.UNKNOWN)
        val remoteAccountDataSource = FakeSearchAccountApi(remoteResponse)
        val repository = DefaultAccountRepository(remoteAccountDataSource, fakeAccountDao)

        val result = repository.searchAccounts("test", null)

        assertTrue(result is Result.Error)
        assertEquals(DataError.Remote.UNKNOWN, result.error)
    }

    @Test
    fun `should return empty list when remote and cache are empty`() = runBlocking {
        val remoteResponse = Result.Success(
            SearchAccountRemote(
                data = SearchData(
                    search = SearchResult(edges = emptyList())
                )
            )
        )
        val remoteAccountDataSource = FakeSearchAccountApi(remoteResponse)
        val repository = DefaultAccountRepository(remoteAccountDataSource, fakeAccountDao)

        val result = repository.searchAccounts("test", null)

        assertTrue(result is Result.Success)
        val accounts = result.data
        assertTrue(accounts.isEmpty())
    }

    @Test
    fun `should prioritize remote data over cache when both are available`() = runBlocking {
        fakeAccountDao.insertAccounts(
            listOf(accountEntity)
        )
        val remoteResponse = successfulRemoteResponse
        val remoteAccountDataSource = FakeSearchAccountApi(remoteResponse)
        val repository = DefaultAccountRepository(remoteAccountDataSource, fakeAccountDao)

        val result = repository.searchAccounts("test", null)

        assertTrue(result is Result.Success)
        val accounts = result.data
        assertEquals(1, accounts.size)
        assertEquals("test_user", accounts.first().username)

        val cachedAccounts = fakeAccountDao.getAccountsByQuery("test")
        assertEquals(1, cachedAccounts.size)
        assertEquals("test_user", cachedAccounts.first().username)
    }

    @Test
    fun `should not cache data when remote fails and cache is empty`() = runBlocking {
        val remoteResponse = Result.Error(DataError.Remote.UNKNOWN)
        val remoteAccountDataSource = FakeSearchAccountApi(remoteResponse)
        val repository = DefaultAccountRepository(remoteAccountDataSource, fakeAccountDao)

        val result = repository.searchAccounts("test", null)

        assertTrue(result is Result.Error)
        assertEquals(DataError.Remote.UNKNOWN, result.error)

        val cachedAccounts = fakeAccountDao.getAccountsByQuery("test")
        assertTrue(cachedAccounts.isEmpty())
    }
}
