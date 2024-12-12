package com.gerhardrvv.githubsearch.core.data

import com.gerhardrvv.githubsearch.BuildConfig

actual fun getApiToken(): String {
    return BuildConfig.GITHUB_API_TOKEN
}
