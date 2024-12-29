package com.example.expansetracker.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IncomeDao {
    @Query("SELECT * FROM Income")
   suspend fun getAll(): List<IncomeItem>

    @Insert
  suspend  fun insert(transactionItem: IncomeItem)

    @Delete
   suspend fun delete(transactionItem: IncomeItem)

    @Query("DELETE FROM Income")
  suspend  fun deleteAll()

  @Query("SELECT SUM(amount) FROM Income")
  suspend fun getTotal():Int

  @Query("SELECT * FROM Income ORDER BY id DESC LIMIT 5")
  suspend fun recentTransaction():List<IncomeItem>

}