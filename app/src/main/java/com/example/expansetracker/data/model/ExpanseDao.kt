package com.example.expansetracker.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpanseDao {
    @Query("SELECT * FROM Expanse")
   suspend fun getAll(): List<TransactionItem>

    @Insert
  suspend  fun insert(transactionItem: TransactionItem)

    @Delete
   suspend fun delete(transactionItem: TransactionItem)

    @Query("DELETE FROM Expanse")
  suspend  fun deleteAll()

  @Query("SELECT SUM(amount) FROM Expanse")
  suspend fun getTotal():Int

  @Query("SELECT * FROM Expanse ORDER BY id DESC LIMIT 5")
  suspend fun recentTransaction():List<TransactionItem>

}