package com.example.myapplication.view.menuUser.addMenu.addMenu

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.MenuData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddMenuViewModel : ViewModel() {


    fun addDataRoom(menu : MenuData){
//        ntar
    }

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

        val result = String.format("%.1f", a * b)
        return result
    }

    fun getCalCarbs(gram : String) : String {
        val a = gram.toDouble()
        return String.format("%.1f", a * 4)
    }

    fun getCalProtein(gram: String): String {
        val a = gram.toDouble()
        return String.format("%.1f", a * 4)
    }

    fun getCalFat(gram: String): String {
        val a = gram.toDouble()
        return String.format("%.1f", a * 9)
    }




}