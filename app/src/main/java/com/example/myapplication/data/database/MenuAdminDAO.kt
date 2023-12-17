package com.example.myapplication.data.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.model.room.MenuAdmin
import com.example.myapplication.data.model.room.MenuData

@Dao
interface MenuAdminDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (menu : MenuAdmin)

    @Query("SELECT * from menuAdmin01_table")
    fun getUnsyncedData() : LiveData<List<MenuAdmin>>


    @Query("DELETE FROM menuAdmin01_table") // Query untuk menghapus semua data dari tabel menu_admin
    fun deleteAllMenuAdmin()


    @Delete
    fun delete(menu: MenuAdmin)






}