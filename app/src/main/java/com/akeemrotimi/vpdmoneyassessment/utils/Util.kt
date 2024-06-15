package com.akeemrotimi.vpdmoneyassessment.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toFormattedDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(this))
}

fun Long.toFormattedDateTime(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
    return formatter.format(Date(this))
}