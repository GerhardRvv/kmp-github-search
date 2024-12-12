package ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.gerhardrvv.githubsearch.feature.search.ui.ExceptionStateScreen
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ExceptionStateScreenTest {

    @Test
    fun allElementsPresent() = runComposeUiTest {
        setContent {
            ExceptionStateScreen(
                title = "Empty State",
                body = "This is an empty state",
                imagePainter = ColorPainter(Color.Gray)
            )
        }
        onNodeWithText("Empty State").assertExists()
        onNodeWithText("This is an empty state").assertExists()
        onNodeWithTag("zero_state_image").assertExists()
    }

    @Test
    fun onlyTitleDisplayed() = runComposeUiTest {
        setContent {
            ExceptionStateScreen(
                title = "Only Title",
                body = null,
                imagePainter = null
            )
        }
        onNodeWithText("Only Title").assertExists()
        onNodeWithText("This is an empty state").assertDoesNotExist()
        onNodeWithTag("zero_state_image").assertDoesNotExist()
    }

    @Test
    fun onlyBodyDisplayed() = runComposeUiTest {
        setContent {
            ExceptionStateScreen(
                title = null,
                body = "Only Body",
                imagePainter = null
            )
        }
        onNodeWithText("Only Body").assertExists()
        onNodeWithText("Empty State").assertDoesNotExist()
        onNodeWithTag("zero_state_image").assertDoesNotExist()
    }

    @Test
    fun onlyImageDisplayed() = runComposeUiTest {
        setContent {
            ExceptionStateScreen(
                title = null,
                body = null,
                imagePainter = ColorPainter(Color.Gray)
            )
        }
        onNodeWithTag("zero_state_image").assertExists()
        onNodeWithText("Empty State").assertDoesNotExist()
        onNodeWithText("This is an empty state").assertDoesNotExist()
    }

    @Test
    fun noElementsDisplayed() = runComposeUiTest {
        setContent {
            ExceptionStateScreen(
                title = null,
                body = null,
                imagePainter = null
            )
        }
        onNodeWithText("Empty State").assertDoesNotExist()
        onNodeWithText("This is an empty state").assertDoesNotExist()
        onNodeWithTag("zero_state_image").assertDoesNotExist()
    }
}
