package com.gerhardrvv.githubsearch.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) darkColors() else lightColors()
    val rememberedColors = remember {
        colors.copy()
    }.apply { updateColorsFrom(colors) }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        content = content
    )
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalColors.current
}
