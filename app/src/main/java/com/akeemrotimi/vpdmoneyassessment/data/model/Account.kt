package com.akeemrotimi.vpdmoneyassessment.data.model

data class Account(
    val id: Int,
    val name: String,
    val bankName: String = "",
    val accountNumber: String = "",
    val accountBalance: Double
)
