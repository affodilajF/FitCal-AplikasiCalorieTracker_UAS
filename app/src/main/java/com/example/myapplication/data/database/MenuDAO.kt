package com.example.myapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.data.model.UserProfile

@Dao
interface MenuDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (menu : MenuData)

    @Update
    fun update(menu:MenuData)

    @Delete
    fun delete(menu:MenuData)

    @get:Query("SELECT * from menu01_table ORDER BY id DESC")
    val allMenus : LiveData<List<MenuData>>

    @Query("SELECT * FROM menu01_table WHERE userId = :userId AND date = :date ORDER BY id DESC")
    fun allMenusByUserId(userId: String, date: String): LiveData<List<MenuData>>

    @Query("SELECT * FROM menu01_table WHERE userId = :userId AND category = :category AND date = :date ORDER BY id DESC")
    fun allMenusByCategory(userId: String, category: String, date: String): LiveData<List<MenuData>>


    //    kalori total
    @Query("SELECT SUM(CAST(calAmount AS INTEGER)) AS totalAmount FROM menu01_table WHERE userId = :userId AND date = :date")
    fun getTotalAmountCal(userId: String, date: String): LiveData<Int>








//    @Query("SELECT SUM(CAST(calAmount AS REAL)) AS totalAmount FROM menu01_table WHERE userId = :userId AND category = :category AND date = :date")
//    fun getTotalCalAmountByCaategory(userId: String, category: String, date: String): LiveData<Double>

//    @Query("SELECT SUM(CAST(calAmount AS INTEGER)) AS totalAmount FROM menu01_table WHERE userId = :userId AND category = :category AND date = :date")
//    fun getTotalCalAmountByCategory(userId: String, category: String, date: String): LiveData<Int>






}