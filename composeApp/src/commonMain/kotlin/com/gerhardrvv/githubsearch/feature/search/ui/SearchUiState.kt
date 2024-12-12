package com.gerhardrvv.githubsearch.feature.search.ui

import com.gerhardrvv.githubsearch.feature.search.data.domain.Account

data class SearchUiState(
    val searchQuery: String = "",
    val searchResult: List<Account> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val isLocalData: Boolean = false,
    val errorMessage: String? = null
)
