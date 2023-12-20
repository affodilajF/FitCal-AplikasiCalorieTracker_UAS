package com.example.myapplication.data.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.model.room.MenuAdmin
import com.example.myapplication.data.model.room.MenuData

@Dao
interface MenuAdminDAO {
//    DAO FOR MenuAdmin DATA CLASS

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (menu : MenuAdmin)

    @get:Query("SELECT * from menuAdmin01_table ORDER BY id DESC")
    val allMenus : LiveData<List<MenuAdmin>>

    @Update
    fun update(menu: MenuAdmin)

    @Query("DELETE FROM menuAdmin01_table")
    fun deleteAll()

    @Delete
    fun delete(menu: MenuAdmin)

}