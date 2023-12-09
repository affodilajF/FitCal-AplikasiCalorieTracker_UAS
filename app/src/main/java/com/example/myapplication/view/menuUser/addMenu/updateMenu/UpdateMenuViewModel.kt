package com.example.myapplication.view.menuUser.addMenu.updateMenu

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.util.SharedPreferencesHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors



class UpdateMenuViewModel() : ViewModel() {

    // database room
    private lateinit var mMenuDao : MenuDAO
    private lateinit var executorService : ExecutorService

    fun initializeDBRoom(context: Context){
        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(context)
        mMenuDao = db!!.menuDao()!!
    }

    fun update(note : MenuData){
        executorService.execute{
            mMenuDao.update(note)
        }
    }

    fun delete(note : MenuData){
        executorService.execute{
            mMenuDao.delete(note)
        }
    }
    fun getTotalCal(servings: String?, calories: String?): String {
        val a = servings?.toDoubleOrNull() ?: 0.0
        val b = calories?.toDoubleOrNull() ?: 0.0

        val result = String.format("%.0f", a * b)
        return result
    }

    fun getCalCarbs(gram : Double) : String {
        val a = gram.toDouble()
        return String.format("%.0f", a * 4)
    }

    fun getCalProtein(gram: Double): String {
        val a = gram.toDouble()
        return String.format("%.0f", a * 4)
    }

    fun getCalFat(gram: Double): String {
        val a = gram
        return String.format("%.0f", a * 9)
    }

}
