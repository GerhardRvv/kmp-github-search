package com.gerhardrvv.githubsearch.feature.search.data.db

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AccountDatabaseConstructor: RoomDatabaseConstructor<AccountDatabase> {
    override fun initialize(): AccountDatabase
}
