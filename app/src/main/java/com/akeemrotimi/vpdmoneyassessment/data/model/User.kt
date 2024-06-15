package com.akeemrotimi.vpdmoneyassessment.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val userId: String = "",
    val firstName: String = "",
    val middleName: String?,
    val lastName: String = "",
    val phoneNumber: String = "",
    val balance: Double = 0.00
)
