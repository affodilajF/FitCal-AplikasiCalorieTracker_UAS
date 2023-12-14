package com.example.myapplication.view.menuUser.addMenu.addMenu

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.util.SharedPreferencesHelper
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
    fun insertMenu(menu : MenuData){
        menu.userId = sharedPreferencesHelper.getUserId().toString()

        val runnable = Runnable { mMenuDao.insert(menu) }
        executorService.execute(runnable)
    }

    fun getUserId(): String {
        return sharedPreferencesHelper.getUserId() ?: ""
    }

}