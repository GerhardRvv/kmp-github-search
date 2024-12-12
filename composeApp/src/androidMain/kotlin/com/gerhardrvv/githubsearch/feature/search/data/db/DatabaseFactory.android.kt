package com.gerhardrvv.githubsearch.feature.search.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<AccountDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(AccountDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath,
        )
    }
}