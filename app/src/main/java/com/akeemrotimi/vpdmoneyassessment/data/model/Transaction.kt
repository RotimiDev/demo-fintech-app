package com.akeemrotimi.vpdmoneyassessment.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val sourceAccount: String,
    val destinationAccount: String,
    val amount: Double,
    val timestamp: Long
)
