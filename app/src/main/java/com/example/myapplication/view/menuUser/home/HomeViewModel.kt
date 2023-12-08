package com.example.myapplication.view.menuUser.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.UserProfile
import com.example.myapplication.util.SharedPreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeViewModel(application: Application) : AndroidViewModel(application) {


    //    sharedpref
    private val sharedPreferencesHelper =
        SharedPreferencesHelper.getInstance(application.applicationContext)


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
    fun formattedDate(date : Date): String {
        val simpleDateFormat = SimpleDateFormat("EEEE, yyyy-MM-dd", Locale.getDefault())
        return simpleDateFormat.format(date)
    }
    fun getExactDateDays( days : Int ): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, days)
        return calendar.time
    }

}