package com.akeemrotimi.vpdmoneyassessment.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.akeemrotimi.vpdmoneyassessment.data.model.Transaction

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM `Transaction` ORDER BY timestamp DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>
}
