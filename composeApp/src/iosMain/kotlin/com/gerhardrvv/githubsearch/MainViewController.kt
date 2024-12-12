package com.gerhardrvv.githubsearch

import androidx.compose.ui.window.ComposeUIViewController
import com.gerhardrvv.githubsearch.feature.search.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }
