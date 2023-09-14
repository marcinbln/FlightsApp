package com.example.flightsapp.core.common

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.example.flightsapp.core.data.source.network.flightOffers.models.ApiErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun String.toUpperCase(): String = uppercase(Locale.getDefault())

fun Context.showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT)
        .show()
}

fun NavOptionsBuilder.clearBackStack(navController: NavHostController) {
    popUpTo(navController.graph.id) { inclusive = true }
}

fun String.toSeparateHourAndMin(): Pair<Long, Long> {
    val duration = Duration.parse(this)
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    return Pair(hours, minutes)
}

fun Pair<Long, Long>.toDurationFormatted(): String {
    val builder = StringBuilder()

    if (this.first > 0) {
        builder
            .append(first)
            .append("h")
    }
    if (this.second > 0) {
        builder
            .append(
                second
                    .toString()
                    .padStart(2, '0')
            )
            .append("m")
    }

    return builder.toString()
}

fun String.toSeparateDateAndTime(): Pair<String, String> {
    val inputDateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
    val date = inputDateTime
        .toLocalDate()
        .toString()
    val time = inputDateTime
        .toLocalTime()
        .toString()
    return Pair(date, time)
}

fun Long.toApiFormat(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(this))
}

fun Long.toFormattedDate(pattern: String = "dd/MM/yyyy"): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(Date(this))
}

fun String.toFormattedTime(pattern: String): String {
    val inputDateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
    return inputDateTime.format(DateTimeFormatter.ofPattern(pattern))
}

fun ResponseBody?.parseError(): ApiErrorResponse? {
    return Gson().fromJson(this?.string(), ApiErrorResponse::class.java)
}
