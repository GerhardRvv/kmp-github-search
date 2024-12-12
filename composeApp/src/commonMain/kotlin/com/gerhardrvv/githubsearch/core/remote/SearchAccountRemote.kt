package com.gerhardrvv.githubsearch.core.remote

import kotlinx.serialization.Serializable

@Serializable
data class SearchAccountRemote(
    val data: SearchData
)

@Serializable
data class SearchData(
    val search: SearchResult
)

@Serializable
data class SearchResult(
    val edges: List<SearchEdge>
)

@Serializable
data class SearchEdge(
    val node: SearchNode
)

@Serializable
data class SearchNode(
    val id: String,
    val login: String,
    val name: String?,
    val avatarUrl: String,
    val repositories: RepositoryInfo
)

@Serializable
data class RepositoryInfo(
    val totalCount: Int
)
