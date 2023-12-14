package com.example.myapplication.view.menuUser.addMenu.updateMenu

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.MenuData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors



class UpdateMenuViewModel() : ViewModel() {

    // database room
    private lateinit var mMenuDao : MenuDAO
    private lateinit var executorService : ExecutorService

    fun initializeDBRoom(context: Context){
        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(context)
        mMenuDao = db!!.menuDao()!!
    }

    fun update(note : MenuData){
        executorService.execute{
            mMenuDao.update(note)
        }
    }

    fun delete(note : MenuData){
        executorService.execute{
            mMenuDao.delete(note)
        }
    }
}
