package com.example.myapplication.view.menuUser.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.util.SharedPreferencesHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    //    sharedpref
    private val sharedPreferencesHelper =
        SharedPreferencesHelper.getInstance(application.applicationContext)

    fun getRemainingCal(achievedCal: Double): String {
        val a = getAmountCal()?.toDouble() ?: 0.0
        val b = achievedCal ?: 0.0
        val c = a - b

        val formattedResult = String.format("%.1f", c.coerceAtLeast(0.0))

        return formattedResult
    }


    fun getPercentProgress(achievedCal: Double) : Int {
//        kalori target perhari
        val a = getAmountCal()?.toDouble() ?: 0.0
//        kalori sekarang
        val b = achievedCal ?: 0.0
//        percent progress
        val c = (b/a)*100

        return c.toInt()

    }


    fun getAllLiveDataByCategory(mMenuDao : MenuDAO, filter : String, date : String): LiveData<List<MenuData>> {
        return mMenuDao.allMenusByCategory(sharedPreferencesHelper.getUserId().toString(), filter, date)
    }

    fun getAmountCalAllLiveDataByUserId(mMenuDao : MenuDAO, date : String): LiveData<Double> {
        return mMenuDao.getTotalAmountCal(sharedPreferencesHelper.getUserId().toString(), date )
    }


//    sharedpref
    fun getUsn():String {
        return sharedPreferencesHelper.getUsername().toString()
    }

    fun getCweight() : String{
        return sharedPreferencesHelper.getCurrentWeight().toString()
    }
    fun getTWeight() : String {
        return sharedPreferencesHelper.getTargetedWeight().toString()
    }

    fun getAmountCal() : String {
        return sharedPreferencesHelper.getDayTargetedCalorie().toString()
    }

    fun getCarbsTarget() : String {
        return sharedPreferencesHelper.getCarbsGram().toString()
    }
    fun getProteinTarget() : String {
        return sharedPreferencesHelper.getProteinGram().toString()
    }

    fun getFatTarget() : String {
        return sharedPreferencesHelper.getFatGram().toString()
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