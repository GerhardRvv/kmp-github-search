package com.gerhardrvv.githubsearch.feature.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gerhardrvv.githubsearch.feature.search.data.domain.Account
import com.gerhardrvv.githubsearch.feature.search.ui.SearchAction
import com.gerhardrvv.githubsearch.feature.search.ui.SearchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class SearchViewModel(): ViewModel() {

    // Dummy data
    private fun generateRandomAccounts(count: Int = 10): List<Account> {
        val random = Random(2)
        return List(count) { index ->
            Account(
                id = "${index + 1}",
                username = "user_${index + 1}",
                avatarUrl = "https://dummyimage.com/600x400/000/fff&text=${index + 1}",
                repoCount = random.nextInt(100),
                isLocal = false
            )
        }
    }

    private val _uiState = MutableStateFlow(SearchUiState().copy(isEmpty = false, searchResult = generateRandomAccounts()))
    val uiState = _uiState
        .onStart {
            emit(SearchUiState(isLoading = true))
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
}
