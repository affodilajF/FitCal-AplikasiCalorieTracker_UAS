package com.example.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.room.MenuAdmin
import com.example.myapplication.data.model.room.MenuData

@Database(entities = [MenuData::class, MenuAdmin::class],
    version = 4,
    exportSchema = false)
abstract class MenuRoomDatabase : RoomDatabase() {

    abstract fun menuDao() : MenuDataDAO?
//    dao MenuData, milik user

    abstract fun menuAdminDao() : MenuAdminDAO?
//    dao MenuAdmin, milik admin

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
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
