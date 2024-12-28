package com.example.expansetracker.data.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TransactionItem::class], version = 1)
abstract class ExpanseDatabase:RoomDatabase() {
    abstract fun getDao():ExpanseDao
}