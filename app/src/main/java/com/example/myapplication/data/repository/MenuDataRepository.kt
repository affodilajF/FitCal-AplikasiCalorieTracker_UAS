package com.example.myapplication.data.repository


import android.content.Context
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.room.MenuData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MenuDataRepository(context: Context) {
    private val mMenuDao: MenuDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MenuRoomDatabase.getDatabase(context)
        mMenuDao = db!!.menuDao()!!
    }


    fun insertMenu(menu : MenuData, userId : String){
        menu.userId = userId

        val runnable = object : Runnable {
            override fun run() {
                mMenuDao.insert(menu)
            }
        }
        executorService.execute(runnable)
    }





}