package com.gerhardrvv.githubsearch.feature.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gerhardrvv.githubsearch.designsystem.theme.AppTheme

@Composable
fun AccountListLoadingState(
    scrollableState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = Modifier
            .background(color = AppTheme.colors.bg02).testTag("account_list_loading_state"),
        state = scrollableState,
        contentPadding = PaddingValues(top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(16) {
            AccountCardShimmer()
        }
    }
}
