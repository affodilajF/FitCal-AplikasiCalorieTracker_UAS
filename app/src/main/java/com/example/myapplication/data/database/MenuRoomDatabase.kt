package com.example.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.room.MenuAdmin
import com.example.myapplication.data.model.room.MenuData

@Database(entities = [MenuData::class, MenuAdmin::class],
    version = 3, // Ubah versi ke versi yang lebih tinggi
    exportSchema = false)
abstract class MenuRoomDatabase : RoomDatabase() {

    abstract fun menuDao() : MenuDAO?
//    dao menuData (milik user)

    abstract fun menuAdminDao() : MenuAdminDAO?
//    dao menu, milik admin

    companion object {
        @Volatile
        private var INSTANCE : MenuRoomDatabase ? = null

        fun getDatabase(context: Context) : MenuRoomDatabase? {
            if(INSTANCE == null){
                synchronized(MenuRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MenuRoomDatabase::class.java, "menu_db"
                    )
                        .fallbackToDestructiveMigration() // Tambahkan fallbackToDestructiveMigration jika perlu
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
