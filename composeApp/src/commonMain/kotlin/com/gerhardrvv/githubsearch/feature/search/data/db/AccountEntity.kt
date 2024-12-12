package com.gerhardrvv.githubsearch.feature.search.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_accounts")
data class AccountEntity(
    @PrimaryKey val id: String,
    val username: String,
    val avatarUrl: String,
    val repoCount: Int,
    val query: String
)
