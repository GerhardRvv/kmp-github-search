package com.gerhardrvv.githubsearch.feature.search.di


import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.gerhardrvv.githubsearch.core.data.HttpClientFactory
import com.gerhardrvv.githubsearch.feature.search.data.db.AccountDatabase
import com.gerhardrvv.githubsearch.feature.search.data.db.DatabaseFactory
import com.gerhardrvv.githubsearch.feature.search.data.domain.AccountRepository
import com.gerhardrvv.githubsearch.feature.search.data.remote.KtorSearchAccountApi
import com.gerhardrvv.githubsearch.feature.search.data.remote.SearchAccountApi
import com.gerhardrvv.githubsearch.feature.search.data.repository.DefaultAccountRepository
import com.gerhardrvv.githubsearch.feature.search.viewmodel.SearchViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorSearchAccountApi).bind<SearchAccountApi>()
    singleOf(::DefaultAccountRepository).bind<AccountRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<AccountDatabase>().accountDao }

    viewModelOf(::SearchViewModel)
}
