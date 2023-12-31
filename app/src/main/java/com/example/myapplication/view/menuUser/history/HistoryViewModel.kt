package com.example.myapplication.view.menuUser.history

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.database.MenuDataDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.room.MenuData
import com.example.myapplication.util.SharedPreferencesHelper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mMenuDataDao : MenuDataDAO
    private lateinit var executorService : ExecutorService

    //    shared pref
    private val sharedPreferencesHelper =
        SharedPreferencesHelper.getInstance(application.applicationContext)

//    initialize all
    fun initDBRoom(context: Context){
        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(context)
        mMenuDataDao = db!!.menuDao()!!
    }

    fun getAllLiveDataByCategory(filter : String, date : String): LiveData<List<MenuData>> {
        return if(filter=="All"){
            mMenuDataDao.allMenusByUserId(sharedPreferencesHelper.getUserId().toString(), date )
        } else {
            mMenuDataDao.allMenusByCategory(sharedPreferencesHelper.getUserId().toString(), filter, date)
        }
    }

//

}