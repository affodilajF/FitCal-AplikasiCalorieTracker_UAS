package com.example.myapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.model.room.MenuData

@Dao
interface MenuDataDAO {
    //    DAO FOR MenuData DATA CLASS
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (menu : MenuData)

    @Update
    fun update(menu: MenuData)

    @Delete
    fun delete(menu: MenuData)

    @get:Query("SELECT * from menu01_table ORDER BY id DESC")
    val allMenus : LiveData<List<MenuData>>

    @Query("SELECT * FROM menu01_table WHERE userId = :userId AND date = :date ORDER BY id DESC")
    fun allMenusByUserId(userId: String, date: String): LiveData<List<MenuData>>

    @Query("SELECT * FROM menu01_table WHERE userId = :userId AND category = :category AND date = :date ORDER BY id DESC")
    fun allMenusByCategory(userId: String, category: String, date: String): LiveData<List<MenuData>>


    //    kalori total yang udah di achieve
    @Query("SELECT SUM(CAST(calAmount AS INTEGER)) AS totalAmount FROM menu01_table WHERE userId = :userId AND date = :date")
    fun getTotalAmountCal(userId: String, date: String): LiveData<Int>


//    kalori total per protein/carbs/fat
    @Query("SELECT SUM(CAST(servings AS REAL) * CAST(carbsGram AS REAL)) AS totalServingTimesCarbs FROM menu01_table WHERE userId = :userId AND date = :date")
    fun getTotalServingTimesCarbs(userId: String, date: String): LiveData<Double>

    @Query("SELECT SUM(CAST(servings AS REAL) * CAST(proteinGram AS REAL)) AS totalServingTimesProtein FROM menu01_table WHERE userId = :userId AND date = :date")
    fun getTotalServingTimesProtein(userId: String, date: String): LiveData<Double>

    @Query("SELECT SUM(CAST(servings AS REAL) * CAST(fatGram AS REAL)) AS totalServingTimesProtein FROM menu01_table WHERE userId = :userId AND date = :date")
    fun getTotalServingTimesFat(userId: String, date: String): LiveData<Double>

}