package com.gerhardrvv.githubsearch.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.gerhardrvv.githubsearch.designsystem.theme.AppTheme

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    fontWeight: FontWeight = FontWeight.Normal,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = AppTheme.colors.text01,
    textDecoration: TextDecoration? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    ProvideTextStyle(value = style) {
        Text(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            textDecoration = textDecoration,
            maxLines = maxLines,
            overflow = overflow
        )
    }
}
