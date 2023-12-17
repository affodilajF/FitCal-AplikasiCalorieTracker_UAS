package com.example.myapplication.util

object Formatter {

    fun formattedInt(param: String): Int {
        return try {
            val value = param.toIntOrNull() ?: 0
            value
        } catch (e: NumberFormatException) {
            0
        }
    }

    fun formattedDouble(param: String): Double {
        return try {
            val value = param.toDoubleOrNull() ?: 0.0
            String.format("%.1f", value).toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }
}