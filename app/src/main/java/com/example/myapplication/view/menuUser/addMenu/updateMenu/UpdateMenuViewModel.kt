package com.example.myapplication.view.menuUser.addMenu.updateMenu

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.util.SharedPreferencesHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
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

    fun getTotalCal100( carbs : Int, protein : Int, fat : Int) : Int {
        val b = (carbs*4) + (protein*4) + (fat*9)
//        val a = b.toInt()

//        val result = String.format("%.0f", a)
        return b
    }

    fun getCalAchieved(param1 : Int, param2 : Int, param3 : Int, param4 : Double): Int {
        val a = (getTotalCal100(param1, param2, param3))*param4
        return a.toInt()

    }
    fun getTotalCal(servings: String?, calories: String?): String {
        val a = servings?.toDoubleOrNull() ?: 0.0
        val b = calories?.toDoubleOrNull() ?: 0.0

        val result = String.format("%.0f", a * b)
        return result
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
