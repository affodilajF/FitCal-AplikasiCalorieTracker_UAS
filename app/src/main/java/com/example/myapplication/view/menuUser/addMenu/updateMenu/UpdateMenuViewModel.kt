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

    fun getTotalCal100(carbs: Int, protein: Int, fat: Int): Int {
        return (carbs * 4) + (protein * 4) + (fat * 9)
    }


    fun getTotalCal(servings: String?, calories: String?): String {
        val a = servings?.toDoubleOrNull() ?: 0.0
        val b = calories?.toDoubleOrNull() ?: 0.0

        return String.format("%.0f", a * b)
    }

    fun getCalCarbs(gram : Int) : String {
        return (gram*4).toString()
    }

    fun getCalProtein(gram: Int): String {
        return (gram*4).toString()
    }

    fun getCalFat(gram: Int): String {
        return (gram*9).toString()
    }

}
