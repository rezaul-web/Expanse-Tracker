package com.example.expansetracker.data.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TransactionItem::class,IncomeItem::class], version = 2 )
abstract class ExpanseDatabase:RoomDatabase() {
    abstract fun getExpanseDao():ExpanseDao
    abstract fun getIncomeDao():IncomeDao
}