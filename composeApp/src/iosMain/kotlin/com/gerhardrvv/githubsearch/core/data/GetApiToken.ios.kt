package com.gerhardrvv.githubsearch.core.data

import platform.Foundation.NSBundle

actual fun getApiToken(): String {
    val token = NSBundle.mainBundle.objectForInfoDictionaryKey("GitHubApiToken") as? String
    return token ?: throw IllegalStateException("GitHub API Token not found in Info.plist")
}
