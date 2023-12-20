package com.example.myapplication.view.menuAdmin.LocalAdminUpdateMenu

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.database.MenuAdminDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.room.MenuAdmin
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AdminUpdateLocalViewModel : ViewModel() {

    //    room db
    private lateinit var mMenuAdminDao : MenuAdminDAO
    private lateinit var executorService : ExecutorService

    fun initializeDBRoom(context: Context){
        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(context)
        mMenuAdminDao = db!!.menuAdminDao()!!
    }

    fun update(note : MenuAdmin){
        executorService.execute{
            mMenuAdminDao.update(note)
        }
    }

    fun delete(note : MenuAdmin){
        executorService.execute{
            mMenuAdminDao.delete(note)
        }
    }


}