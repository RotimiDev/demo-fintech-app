package com.akeemrotimi.vpdmoneyassessment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akeemrotimi.vpdmoneyassessment.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    suspend fun getUserById(userId: String): User?

    @Query("SELECT balance FROM users WHERE userId = :userId LIMIT 1")
    suspend fun getUserBalance(userId: Int): Double

    @Query("UPDATE users SET balance = :newBalance WHERE userId = :userId")
    suspend fun updateUserBalance(userId: Int, newBalance: Double)
}
