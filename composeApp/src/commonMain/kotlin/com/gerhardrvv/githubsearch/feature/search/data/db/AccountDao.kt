package com.gerhardrvv.githubsearch.feature.search.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AccountDao {
    @Upsert
    suspend fun upsertAccounts(accounts: List<AccountEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accountEntities: List<AccountEntity>)

    @Query("SELECT * FROM cached_accounts WHERE username = :username")
    suspend fun getAccountsByUsername(username: String): List<AccountEntity>

    @Query("SELECT * FROM cached_accounts WHERE query = :query")
    suspend fun getAccountsByQuery(query: String): List<AccountEntity>

    @Query("DELETE FROM cached_accounts WHERE query = :query")
    suspend fun deleteAccountsByQuery(query: String)
}
