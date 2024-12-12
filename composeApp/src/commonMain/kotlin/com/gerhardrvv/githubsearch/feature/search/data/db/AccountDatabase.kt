package com.gerhardrvv.githubsearch.feature.search.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AccountEntity::class],
    version = 1,
    exportSchema = false
)
@ConstructedBy(AccountDatabaseConstructor::class)
abstract class AccountDatabase: RoomDatabase() {
    abstract val accountDao: AccountDao

    companion object {
        const val DB_NAME = "accounts_db"
    }
}
