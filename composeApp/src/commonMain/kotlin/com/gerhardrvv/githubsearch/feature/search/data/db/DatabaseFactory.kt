package com.gerhardrvv.githubsearch.feature.search.data.db

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<AccountDatabase>
}
