package com.gerhardrvv.githubsearch.feature.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gerhardrvv.githubsearch.core.domain.onError
import com.gerhardrvv.githubsearch.core.domain.onSuccess
import com.gerhardrvv.githubsearch.feature.search.data.domain.Account
import com.gerhardrvv.githubsearch.feature.search.data.domain.AccountRepository
import com.gerhardrvv.githubsearch.feature.search.ui.SearchAction
import com.gerhardrvv.githubsearch.feature.search.ui.SearchUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val accountRepository: AccountRepository
): ViewModel() {

    private val resultLimit = 50
    private val cachedAccounts = emptyList<Account>()
    private var searchJob: Job? = null

    private val _uiState = MutableStateFlow(SearchUiState().copy(isEmpty = true))
    val uiState = _uiState
        .onStart {
            if (cachedAccounts.isEmpty()) {
                observerSearchQuery()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _uiState.value
        )

    fun onAction(action: SearchAction) {
        when(action) {
            is SearchAction.OnSearchQueryChange -> {
                _uiState.update {
                    it.copy(searchQuery = action.query)
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observerSearchQuery() {
        uiState
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(2000L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _uiState.update {
                            it.copy(errorMessage = null, isEmpty = true)
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchAccounts(query)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun searchAccounts(query: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, isEmpty = false) }

        accountRepository
            .searchAccounts(query = query, resultLimit = resultLimit)
            .onSuccess { searchResults ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        isEmpty = searchResults.isEmpty(),
                        isLocalData = searchResults.firstOrNull()?.isLocal ?: false,
                        searchResult = searchResults
                    )
                }
            }
            .onError { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.name,
                        isEmpty = false,
                        isLocalData = false,
                        searchResult = emptyList()
                    )
                }
            }
    }
}
