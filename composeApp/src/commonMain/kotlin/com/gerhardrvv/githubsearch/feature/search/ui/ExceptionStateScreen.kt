package com.gerhardrvv.githubsearch.feature.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gerhardrvv.githubsearch.designsystem.component.TextField
import com.gerhardrvv.githubsearch.designsystem.theme.AppTheme

@Composable
fun ExceptionStateScreen(title: String?, body: String?, imagePainter: Painter?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.bg02).testTag("zero_state_screen"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imagePainter != null) {
            Image(
                painter = imagePainter,
                contentDescription = "zero state image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .testTag("zero_state_image")
            )
        }

        if (title != null) {
            TextField(
                text = title,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        }

        if (body != null) {
            TextField(
                text = body,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
