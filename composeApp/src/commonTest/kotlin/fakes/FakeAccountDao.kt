package fakes

import com.gerhardrvv.githubsearch.feature.search.data.db.AccountDao
import com.gerhardrvv.githubsearch.feature.search.data.db.AccountEntity

class FakeAccountDao : AccountDao {

    private val accounts = mutableListOf<AccountEntity>()

    override suspend fun upsertAccounts(accounts: List<AccountEntity>) {
        this.accounts.removeAll(accounts)
        this.accounts.addAll(accounts)
    }

    override suspend fun insertAccounts(accountEntities: List<AccountEntity>) {
        this.accounts.addAll(accountEntities)
    }

    override suspend fun getAccountsByUsername(username: String): List<AccountEntity> {
        return accounts.filter { it.username == username }
    }

    override suspend fun getAccountsByQuery(query: String): List<AccountEntity> {
        return accounts.filter { it.query == query }
    }

    override suspend fun deleteAccountsByQuery(query: String) {
        accounts.removeAll { it.query == query }
    }
}
