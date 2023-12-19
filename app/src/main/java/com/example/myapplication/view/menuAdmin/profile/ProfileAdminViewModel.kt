package com.example.myapplication.view.menuAdmin.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.util.SharedPreferencesHelper

class ProfileAdminViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferencesHelper = SharedPreferencesHelper.getInstance(application.applicationContext)

    fun logout(){
        sharedPreferencesHelper.clear()
    }





}