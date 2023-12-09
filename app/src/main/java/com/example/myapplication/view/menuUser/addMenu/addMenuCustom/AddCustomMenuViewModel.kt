package com.example.myapplication.view.menuUser.addMenu.addMenuCustom

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddCustomMenuViewModel : ViewModel() {

    fun getTodayDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }
    fun getFormattedDate(date : Date): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return simpleDateFormat.format(date)
    }
    fun getTotalCal(servings: String?, calories: String?): String {
        val a = servings?.toDoubleOrNull() ?: 0.0
        val b = calories?.toDoubleOrNull() ?: 0.0

        val result = String.format("%.0f", a * b)
        return result
    }
    fun getCalculatedAllCalories(gram : String?, gram2 : String?, gram3 : String?) : String{
        val a = getCalCarbs(gram).toDouble()
        val b = getCalProtein(gram2).toDouble()
        val c = getCalFat(gram3).toDouble()

//        return (a + b + c).toString()
        val totalCalories = a + b + c
        return String.format("%.0f", totalCalories)
    }

    fun getCalCarbs(gram : String?) : String {
        val a = gram?.toDoubleOrNull() ?: 0.0
        return String.format("%.0f", a * 4)
    }

    fun getCalProtein(gram: String?): String {
        val a = gram?.toDoubleOrNull() ?: 0.0
        return String.format("%.0f", a * 4)
    }

    fun getCalFat(gram: String?): String {
        val a = gram?.toDoubleOrNull() ?: 0.0
        return String.format("%.0f", a * 9)
    }

}