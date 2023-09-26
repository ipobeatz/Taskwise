package com.example.taskwise.util

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import java.text.SimpleDateFormat


object TimeChecker {

    // Saat biçimleri listesi
    private val hourFormats = listOf(
        DateTimeFormatter.ofPattern("HH:mm"),
        DateTimeFormatter.ofPattern("H:mm"),
        DateTimeFormatter.ofPattern("HH:m"),
        DateTimeFormatter.ofPattern("H:m")
    )

    fun checkTime(time: String): Boolean {
        try {
            val currentTime = LocalTime.now()
            var timeTo: LocalTime? = null

            // Farklı saat biçimlerini deneyin
            for (format in hourFormats) {
                try {
                    timeTo = LocalTime.parse(time, format)
                    break // Geçerli bir biçim bulunca döngüden çıkın
                } catch (e: DateTimeParseException) {
                    // Bu biçimle eşleşmezse bir sonraki biçimi deneyin
                }
            }

            if (timeTo != null && timeTo.isBefore(currentTime)) {
                Log.d("TimeChecker", "Time is in the past")
                return false
            }

            if (timeTo != null) {
                Log.d("TimeChecker", "Time is in the future")
                return true
            } else {
                Log.e("TimeChecker", "Invalid time format: $time")
                return false
            }
        } catch (e: Exception) {
            Log.e("TimeChecker", "Error: $e")
            return false
        }
    }
}