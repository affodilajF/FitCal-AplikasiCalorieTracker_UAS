package com.example.myapplication.view.menuUser.history

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mMenuDao : MenuDAO
    private lateinit var executorService : ExecutorService

//    initialize all
    fun initDBRoom(context: Context){
        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(context)
        mMenuDao = db!!.menuDao()!!
    }

//    shared pref
    private val sharedPreferencesHelper =
        SharedPreferencesHelper.getInstance(application.applicationContext)


    fun getAllLiveDataByCategory(filter : String, date : String): LiveData<List<MenuData>> {
        return if(filter=="All"){
            mMenuDao.allMenusByUserId(sharedPreferencesHelper.getUserId().toString(), date )
        } else {
            mMenuDao.allMenusByCategory(sharedPreferencesHelper.getUserId().toString(), filter, date)
        }
    }

    fun getTodayDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }
    fun getFormattedDate(date : Date): String {
        val simpleDateFormat = SimpleDateFormat("EEEE, yyyy-MM-dd", Locale.getDefault())
        return simpleDateFormat.format(date)
    }
    fun getExactDateDays( days : Int ): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, days)
        return calendar.time
    }

}