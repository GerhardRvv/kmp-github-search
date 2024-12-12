package com.gerhardrvv.githubsearch.feature.search.data.domain

data class Account(
    val id: String,
    val username: String,
    val avatarUrl: String,
    val repoCount: Int,
    val isLocal: Boolean = false
)
