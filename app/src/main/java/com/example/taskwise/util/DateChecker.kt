package com.example.taskwise.util

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import com.example.taskwise.data.model.Task
import com.example.taskwise.util.Constants.toDays
import com.example.taskwise.util.Constants.toHours
import com.example.taskwise.util.Constants.toMills
import com.example.taskwise.util.Constants.toMinute
import java.text.SimpleDateFormat
import java.util.Date

private const val ONE_DAY_MILLIS = 24 * 60 * 60 * 1000L  // Bir günü temsil eden milisaniye cinsinden değer

@SuppressLint("SimpleDateFormat")
object DateChecker {

    @SuppressLint("SimpleDateFormat")
    fun getDifferentDays(date: String): Int {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val selectedDate: Date = sdf.parse(date) as Date
        val currentDate = Date()

        if (selectedDate.before(currentDate)) {
            // Seçilen tarih geçmişte
            val diff: Long = currentDate.time - selectedDate.time
            val days = diff / (24 * 60 * 60 * 1000L) // Bir günü temsil eden milisaniye cinsinden değer
            Log.d("DateChecker", "Selected date is in the past, $days days agos.")
            return days.toInt()
        } else {
            // Seçilen tarih gelecekte
            val diff: Long = selectedDate.time - currentDate.time
            val days = diff / (24 * 60 * 60 * 100L) // Bir günü temsil eden milisaniye cinsinden değer
            Log.d("DateChecker", "Selected date is in the future, $days days ahead.")
            return days.toInt()
        }
    }
}