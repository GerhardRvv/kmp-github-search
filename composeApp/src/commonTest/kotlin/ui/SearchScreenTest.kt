package ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.gerhardrvv.githubsearch.feature.search.data.domain.Account
import com.gerhardrvv.githubsearch.feature.search.ui.SearchScreenContent
import com.gerhardrvv.githubsearch.feature.search.ui.SearchUiState
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class SearchListContentTest {

    @Test
    fun displaysLoadingState() = runComposeUiTest {
        setContent {
            SearchScreenContent(
                state = SearchUiState(isLoading = true),
                onAction = {}
            )
        }
        onNodeWithTag("account_list_loading_state").assertExists()
        val nodes = onAllNodes(hasTestTag("account_list_card_shimmer"))
        nodes.onFirst().assertExists()
    }

    @Test
    fun displaysEmptyState() = runComposeUiTest {
        setContent {
            SearchScreenContent(
                state = SearchUiState(
                    isEmpty = true
                ),
                onAction = {}
            )
        }

        onNodeWithTag("zero_state_screen").assertExists()
        onNodeWithTag("zero_state_image").assertExists()
        onNodeWithText("No search results yet").assertExists()
        onNodeWithText("Start typing to search for a user or organization.").assertExists()
    }

    @Test
    fun displaysSearchResults() = runComposeUiTest {
        val accounts = listOf(
            Account(id = "1", username = "user1", avatarUrl = "null", repoCount = 1, isLocal = false),
            Account(id = "2", username = "user2", avatarUrl = "null", repoCount = 1, isLocal = false)
        )

        setContent {
            SearchScreenContent(
                state = SearchUiState(
                    searchResult = accounts
                ),
                onAction = {}
            )
        }

        onNodeWithText("user1").assertExists()
        onNodeWithText("user2").assertExists()
    }

    @Test
    fun displaysOfflineSearchResults() = runComposeUiTest {
        val accounts = listOf(
            Account(id = "1", username = "user1", avatarUrl = "null", repoCount = 1, isLocal = true),
            Account(id = "2", username = "user2", avatarUrl = "null", repoCount = 1, isLocal = true)
        )

        setContent {
            SearchScreenContent(
                state = SearchUiState(
                    searchResult = accounts,
                    isLocalData = accounts.first().isLocal
                ),
                onAction = {}
            )
        }

        onNodeWithText("Offline results").assertExists()
        onNodeWithText("user1").assertExists()
        onNodeWithText("user2").assertExists()
    }
}
