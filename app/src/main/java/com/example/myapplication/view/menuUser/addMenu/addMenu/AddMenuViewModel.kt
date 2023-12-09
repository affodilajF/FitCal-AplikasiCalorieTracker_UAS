package com.example.myapplication.view.menuUser.addMenu.addMenu

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.util.SharedPreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddMenuViewModel(application: Application) : AndroidViewModel(application) {

// database room
    private lateinit var mMenuDao : MenuDAO
    private lateinit var executorService : ExecutorService

    private val sharedPreferencesHelper =
        SharedPreferencesHelper.getInstance(application.applicationContext)

    fun initializeDBRoom(context: Context){
        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(context)
        mMenuDao = db!!.menuDao()!!
    }
    fun insertRoom(menu : MenuData){
        menu.userId = sharedPreferencesHelper.getUserId().toString()

        val runnable = object : Runnable {
            override fun run() {
                mMenuDao.insert(menu)
            }
        }
        executorService.execute(runnable)
    }

    fun getUserId(): String {
        return sharedPreferencesHelper.getUserId() ?: ""
    }


    fun getTodayDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }

    fun getFormattedDate(date : Date): String {
        val simpleDateFormat = SimpleDateFormat("EEEE, yyyy-MM-dd", Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    fun getTotalCal(servings: String?, calories: String?): String {
        val a = servings?.toDoubleOrNull() ?: 0.0
        val b = calories?.toDoubleOrNull() ?: 0.0

        val result = String.format("%.0f", a * b)
        return result
    }

    fun getCalCarbs(gram : String) : String {
        val a = gram.toDouble()
        return String.format("%.0f", a * 4)
    }

    fun getCalProtein(gram: String): String {
        val a = gram.toDouble()
        return String.format("%.0f", a * 4)
    }

    fun getCalFat(gram: String): String {
        val a = gram.toDouble()
        return String.format("%.0f", a * 9)
    }

}


