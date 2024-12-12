package mapper

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.gerhardrvv.githubsearch.core.remote.RepositoryInfo
import com.gerhardrvv.githubsearch.core.remote.SearchNode
import com.gerhardrvv.githubsearch.feature.search.data.db.AccountEntity
import com.gerhardrvv.githubsearch.feature.search.data.domain.Account
import com.gerhardrvv.githubsearch.feature.search.data.mapper.toAccount
import com.gerhardrvv.githubsearch.feature.search.data.mapper.toAccountEntity
import kotlin.test.Test

class AccountMappersTest {

    private val sampleSearchNode = SearchNode(
        id = "123",
        name = "John Doe",
        login = "johndoe",
        avatarUrl = "https://example.com/avatar.jpg",
        repositories = RepositoryInfo(totalCount = 5)
    )

    private val sampleAccount = Account(
        id = "123",
        username = "John Doe",
        avatarUrl = "https://example.com/avatar.jpg",
        repoCount = 5,
        isLocal = false
    )

    private val sampleAccountEntity = AccountEntity(
        id = "123",
        username = "John Doe",
        avatarUrl = "https://example.com/avatar.jpg",
        repoCount = 5,
        query = "sampleQuery"
    )

    @Test
    fun toAccount() {
        val account = sampleSearchNode.toAccount()
        assertThat(account).isEqualTo(sampleAccount)
    }

    @Test
    fun `toAccount should use login if name is null`() {
        val searchNode = sampleSearchNode.copy(name = null, login = "janedoe", id = "456")
        val expectedAccount = sampleAccount.copy(id = "456", username = "janedoe")
        val account = searchNode.toAccount()
        assertThat(account).isEqualTo(expectedAccount)
    }

    @Test
    fun toAccountEntity() {
        val accountEntity = sampleAccount.toAccountEntity("sampleQuery")
        assertThat(accountEntity).isEqualTo(sampleAccountEntity)
    }

    @Test
    fun `toAccountEntity should handle empty query`() {
        val accountEntity = sampleAccount.toAccountEntity("")
        val expectedEntity = sampleAccountEntity.copy(query = "")
        assertThat(accountEntity).isEqualTo(expectedEntity)
    }

    @Test
    fun `toAccount should convert AccountEntity correctly`() {
        val account = sampleAccountEntity.toAccount()
        assertThat(account).isEqualTo(sampleAccount.copy(isLocal = true))
    }

    @Test
    fun `toAccount should handle empty repository count`() {
        val searchNode = sampleSearchNode.copy(repositories = RepositoryInfo(totalCount = 0), id = "333")
        val expectedAccount = sampleAccount.copy(repoCount = 0, id = "333")
        val account = searchNode.toAccount()
        assertThat(account).isEqualTo(expectedAccount)
    }

    @Test
    fun `toAccountEntity should handle negative repo count gracefully`() {
        val account = sampleAccount.copy(repoCount = -10, id = "444", username = "Negative Repo")
        val expectedEntity = sampleAccountEntity.copy(repoCount = -10, id = "444", username = "Negative Repo")
        val accountEntity = account.toAccountEntity(query = "sampleQuery")
        assertThat(accountEntity).isEqualTo(expectedEntity)
    }

    @Test
    fun `AccountEntity conversion preserves isLocal state`() {
        val account = sampleAccountEntity.copy(query = "preserve").toAccount()
        assertThat(account.isLocal).isTrue()
        assertThat(account.id).isEqualTo("123")
    }
}
