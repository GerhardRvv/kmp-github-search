package com.gerhardrvv.githubsearch.feature.search.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gerhardrvv.githubsearch.designsystem.component.TextField
import com.gerhardrvv.githubsearch.designsystem.theme.AppTheme
import com.gerhardrvv.githubsearch.feature.search.data.domain.Account
import githubsearch.composeapp.generated.resources.Res
import githubsearch.composeapp.generated.resources.error_offline_results
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchList(
    accounts: List<Account>,
    modifier: Modifier = Modifier,
    scrollableState: LazyListState = rememberLazyListState(),
    isLocalData: Boolean,
) {
    LazyColumn(
        modifier = modifier
            .background(color = AppTheme.colors.bg02),
        state = scrollableState,
        contentPadding = PaddingValues(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isLocalData) {
            stickyHeader {
                Card(
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.colors.bg02),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            text = stringResource(Res.string.error_offline_results),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }

        items(
            items = accounts,
            key = { it.id }
        ) {
            AccountListCard(
                account = it,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
