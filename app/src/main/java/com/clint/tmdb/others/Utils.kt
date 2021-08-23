package com.clint.tmdb.others

import android.content.Context
import com.clint.tmdb.R
import java.text.SimpleDateFormat
import java.util.*


private const val DISPLAY_DATE_FORMAT = "EE, dd MMM yyyy"
private const val DISPLAY_DATE_FORMAT_DATE_ONLY = "yyyy"
private const val RECIEVED_DATE_FORMAT = "yyyy-MM-dd"

fun Date.formatToViewDateDefaults(): String {
    val sdf = SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToViewDateOnly(): String {
    val sdf = SimpleDateFormat(DISPLAY_DATE_FORMAT_DATE_ONLY, Locale.getDefault())
    return sdf.format(this)
}


fun String.convertStringToDate(): Date {
    val format = SimpleDateFormat(RECIEVED_DATE_FORMAT)
    val date: Date = format.parse(this)
    return date
}

fun Int.convertMinutesToHours(context: Context): String {
    val hour = this / 60
    val min = this % 60
    return context.getString(R.string.duration, hour, min)
}
