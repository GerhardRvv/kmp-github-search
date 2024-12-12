package com.gerhardrvv.githubsearch.feature.search.data.mapper

import com.gerhardrvv.githubsearch.core.remote.SearchNode
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
