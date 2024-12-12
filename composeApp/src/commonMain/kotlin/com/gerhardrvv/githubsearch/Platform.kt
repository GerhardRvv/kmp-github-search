package com.gerhardrvv.githubsearch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform