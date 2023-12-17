package com.example.myapplication.view.getStarted

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.myapplication.util.SharedPreferencesHelper

class SplashActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferencesHelper = SharedPreferencesHelper.getInstance(application.applicationContext)

    fun checkLoginStatus() : Boolean{
        return sharedPreferencesHelper.isLoggedIn()
    }

    fun getRole():String{
        return sharedPreferencesHelper.getUserRole().toString()
    }
}