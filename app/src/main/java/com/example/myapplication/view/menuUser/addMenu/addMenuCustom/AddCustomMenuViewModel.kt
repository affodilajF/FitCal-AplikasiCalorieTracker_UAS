package com.example.myapplication.view.menuUser.addMenu.addMenuCustom

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.room.MenuData
import com.example.myapplication.util.SharedPreferencesHelper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddCustomMenuViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferencesHelper =
        SharedPreferencesHelper.getInstance(application.applicationContext)

    private lateinit var mMenuDao : MenuDAO
    private lateinit var executorService : ExecutorService


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





}