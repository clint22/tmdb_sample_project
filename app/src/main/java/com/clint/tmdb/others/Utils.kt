package com.clint.tmdb.others

import java.text.SimpleDateFormat
import java.util.*


private const val DISPLAY_DATE_FORMAT = "EE, dd MMM yyyy"

fun Date.formatToViewDateDefaults(): String{
    val sdf= SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault())
    return sdf.format(this)
}

