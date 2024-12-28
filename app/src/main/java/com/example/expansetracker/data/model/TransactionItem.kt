package com.example.expansetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Expanse")
data class TransactionItem(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    val text:String,
    val amount:Int,
    val date:String
)
