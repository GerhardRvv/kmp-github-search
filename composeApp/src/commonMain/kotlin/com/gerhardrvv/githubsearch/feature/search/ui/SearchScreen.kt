package com.gerhardrvv.githubsearch.feature.search.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gerhardrvv.githubsearch.designsystem.theme.AppTheme
import com.gerhardrvv.githubsearch.feature.search.viewmodel.SearchViewModel
import githubsearch.composeapp.generated.resources.Res
import githubsearch.composeapp.generated.resources.empty_search_state_subtitle
import githubsearch.composeapp.generated.resources.empty_search_state_title
import githubsearch.composeapp.generated.resources.error_message_body
import githubsearch.composeapp.generated.resources.error_message_title
import githubsearch.composeapp.generated.resources.img_disconnected
import githubsearch.composeapp.generated.resources.img_folder
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreenContent(
        state = state.value,
        onAction = viewModel::onAction
    )
}

@Composable
fun SearchScreenContent(
    state: SearchUiState,
    onAction: (SearchAction) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val searchResultListState = rememberLazyListState()

    LaunchedEffect(state.searchResult) {
        searchResultListState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.bg01)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(SearchAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = AppTheme.colors.bg02,
            shape = RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp
            )
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                when {
                    state.isLoading -> AccountListLoadingState()
                    state.isEmpty -> {
                        ExceptionStateScreen(
                            title = stringResource(Res.string.empty_search_state_title),
                            body = stringResource(Res.string.empty_search_state_subtitle),
                            imagePainter = painterResource(Res.drawable.img_folder)
                        )
                    }
                    state.errorMessage != null -> {
                        ExceptionStateScreen(
                            title = stringResource(Res.string.error_message_title),
                            body = stringResource(Res.string.error_message_body),
                            imagePainter = painterResource(Res.drawable.img_disconnected)
                        )
                    }

                    else -> {
                        SearchList(
                            accounts = state.searchResult,
                            modifier = Modifier.fillMaxSize(),
                            scrollableState = searchResultListState,
                            isLocalData = state.isLocalData
                        )
                    }
                }
            }
        }
    }
}
