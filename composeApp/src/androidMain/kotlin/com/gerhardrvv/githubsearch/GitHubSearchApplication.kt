package com.gerhardrvv.githubsearch

import android.app.Application
import com.gerhardrvv.githubsearch.feature.search.di.initKoin
import org.koin.android.ext.koin.androidContext

class GitHubSearchApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@GitHubSearchApplication)
        }
    }
}
