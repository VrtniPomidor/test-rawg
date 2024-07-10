package com.example.testrawg.common.utils

import android.util.Log
import androidx.core.net.ParseException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Check if any of the arguments is null
 *
 * @param args Any of the args
 * @return Boolean true if any of arguments is null, false otherwise
 */
fun areAnyNull(vararg args: Any?): Boolean {
    return args.any { it == null }
}

fun parseDate(date: String): String = try {
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
    val localDate = LocalDate.parse(date)
    localDate.format(formatter)
} catch (e: ParseException) {
    Log.e("Parse Date", "Error while parsing date $date", e)
    date
}
