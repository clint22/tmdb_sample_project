package com.clint.tmdb.others

import java.text.SimpleDateFormat
import java.util.*


private const val DISPLAY_DATE_FORMAT = "EE, dd MMM yyyy"
private const val RECIEVED_DATE_FORMAT = "yyyy-MM-dd"
fun Date.formatToViewDateDefaults(): String {
    val sdf = SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault())
    return sdf.format(this)
}

fun String.convertStringToDate(): Date {
    val format = SimpleDateFormat(RECIEVED_DATE_FORMAT)
    val date: Date = format.parse(this)
    return date
}
