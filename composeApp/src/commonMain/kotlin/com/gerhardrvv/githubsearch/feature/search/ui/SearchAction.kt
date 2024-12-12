package com.gerhardrvv.githubsearch.feature.search.ui

sealed interface SearchAction {
    data class OnSearchQueryChange(val query: String): SearchAction
}
