package com.gerhardrvv.githubsearch.feature.search.data.mapper

import com.gerhardrvv.githubsearch.core.remote.SearchNode
import com.gerhardrvv.githubsearch.feature.search.data.db.AccountEntity
import com.gerhardrvv.githubsearch.feature.search.data.domain.Account

fun SearchNode.toAccount(): Account {
    return Account(
        id = id,
        username = name ?: login,
        avatarUrl = avatarUrl,
        repoCount = repositories.totalCount,
        isLocal = false
    )
}

fun Account.toAccountEntity(query: String) = AccountEntity(
    id = this.id,
    username = this.username,
    avatarUrl = this.avatarUrl,
    repoCount = this.repoCount,
    query = query
)

fun AccountEntity.toAccount() = Account(
    id = this.id,
    username = this.username,
    avatarUrl = this.avatarUrl,
    repoCount = this.repoCount,
    isLocal = true
)
