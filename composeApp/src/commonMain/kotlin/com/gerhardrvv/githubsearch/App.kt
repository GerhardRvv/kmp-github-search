package com.gerhardrvv.githubsearch

import androidx.compose.runtime.*
import com.gerhardrvv.githubsearch.designsystem.theme.AppTheme
import com.gerhardrvv.githubsearch.feature.search.ui.SearchScreen
import com.gerhardrvv.githubsearch.feature.search.viewmodel.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel = koinViewModel<SearchViewModel>()
    AppTheme {
        SearchScreen(
            viewModel = viewModel
        )
    }
}
