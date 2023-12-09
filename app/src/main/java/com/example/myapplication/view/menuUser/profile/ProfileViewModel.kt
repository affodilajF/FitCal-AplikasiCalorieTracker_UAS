package com.example.myapplication.view.menuUser.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.myapplication.util.SharedPreferencesHelper

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferencesHelper = SharedPreferencesHelper.getInstance(application.applicationContext)

    fun logout(){
        sharedPreferencesHelper.clear()
    }

    fun getUsn() : String? {
        return sharedPreferencesHelper.getUsername()
    }

    fun getPhone() : String? {
        return sharedPreferencesHelper.getUserPhone()
    }
}