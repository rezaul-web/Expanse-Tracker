package com.example.expansetracker.util

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS Income (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                text TEXT NOT NULL,
                date TEXT NOT NULL,
                amount INTEGER NOT NULL
            )
            """.trimIndent()
        )
        Log.d("Migration", "Migration from version 1 to 2 executed successfully.")
    }
}

