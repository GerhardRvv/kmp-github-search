package com.gerhardrvv.githubsearch.feature.search.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gerhardrvv.githubsearch.designsystem.component.TextField
import com.gerhardrvv.githubsearch.designsystem.theme.AppTheme
import githubsearch.composeapp.generated.resources.Res
import githubsearch.composeapp.generated.resources.close_hint
import githubsearch.composeapp.generated.resources.search_hint
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onImeSearch: () -> Unit,
    modifier: Modifier = Modifier
) {

    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = AppTheme.colors.accent01,
            backgroundColor = AppTheme.colors.accent01
        )
    ) {
        OutlinedTextField(
            modifier = modifier
                .heightIn(max = 56.dp)
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = AppTheme.colors.bg02,
                ).minimumInteractiveComponentSize().testTag("search_bar"),
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = AppTheme.colors.text01,
                focusedBorderColor = AppTheme.colors.accent01,
            ),
            placeholder = {
                TextField(
                    text = stringResource(Res.string.search_hint),
                    color = AppTheme.colors.text02
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = AppTheme.colors.text01
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = AppTheme.colors.text02.copy(alpha = 0.7f),
                )
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    onImeSearch()
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = searchQuery.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(
                        onClick = {
                            onSearchQueryChange("")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(Res.string.close_hint),
                            tint = AppTheme.colors.text01,
                        )
                    }
                }
            }
        )
    }
}
