package com.example.myapplication.view.menuUser.addMenu.updateMenu

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.database.MenuDataDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.room.MenuData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors



class UpdateMenuViewModel() : ViewModel() {

    // database room
    private lateinit var mMenuDataDao : MenuDataDAO
    private lateinit var executorService : ExecutorService

    fun initializeDBRoom(context: Context){
        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(context)
        mMenuDataDao = db!!.menuDao()!!
    }

    fun update(note : MenuData){
        executorService.execute{
            mMenuDataDao.update(note)
        }
    }

    fun delete(note : MenuData){
        executorService.execute{
            mMenuDataDao.delete(note)
        }
    }
}
