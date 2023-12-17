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
interface MenuDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (menu : MenuData)

    @Update
    fun update(menu: MenuData)

    @Delete
    fun delete(menu: MenuData)

    @get:Query("SELECT * from menu01_table ORDER BY id DESC")
    val allMenus : LiveData<List<MenuData>>

//    @Query("SELECT * FROM menu01_table WHERE userId = :userId AND date = :date ORDER BY id DESC")
//    fun allMenusByUserId(userId: String, date: String): LiveData<List<MenuData>>

    @Query("SELECT * FROM menu01_table WHERE userId = :userId AND date = :date ORDER BY id DESC")
    fun allMenusByUserId(userId: String, date: String): LiveData<List<MenuData>>

    @Query("SELECT * FROM menu01_table WHERE userId = :userId AND category = :category AND date = :date ORDER BY id DESC")
    fun allMenusByCategory(userId: String, category: String, date: String): LiveData<List<MenuData>>


    //    kalori total
    @Query("SELECT SUM(CAST(calAmount AS INTEGER)) AS totalAmount FROM menu01_table WHERE userId = :userId AND date = :date")
    fun getTotalAmountCal(userId: String, date: String): LiveData<Int>

//    carbs kalori total
//    @Query("SELECT SUM(CAST(carbsGram AS INTEGER)) AS totalAmountCarbs FROM menu01_table WHERE userId = :userId AND date = :date")
//    fun getTotalAmountCalCarbs(userId: String, date: String): LiveData<Int>

//    serving
//    @Query("SELECT SUM(CAST(servings AS REAL)) AS totalAmountCarbs FROM menu01_table WHERE userId = :userId AND date = :date")
//    fun getTotalServing(userId: String, date: String): LiveData<Double>

//    kalori total
    @Query("SELECT SUM(CAST(servings AS REAL) * CAST(carbsGram AS REAL)) AS totalServingTimesCarbs FROM menu01_table WHERE userId = :userId AND date = :date")
    fun getTotalServingTimesCarbs(userId: String, date: String): LiveData<Double>

    @Query("SELECT SUM(CAST(servings AS REAL) * CAST(proteinGram AS REAL)) AS totalServingTimesProtein FROM menu01_table WHERE userId = :userId AND date = :date")
    fun getTotalServingTimesProtein(userId: String, date: String): LiveData<Double>









//    @Query("SELECT SUM(CAST(calAmount AS REAL)) AS totalAmount FROM menu01_table WHERE userId = :userId AND category = :category AND date = :date")
//    fun getTotalCalAmountByCaategory(userId: String, category: String, date: String): LiveData<Double>

//    @Query("SELECT SUM(CAST(calAmount AS INTEGER)) AS totalAmount FROM menu01_table WHERE userId = :userId AND category = :category AND date = :date")
//    fun getTotalCalAmountByCategory(userId: String, category: String, date: String): LiveData<Int>






}