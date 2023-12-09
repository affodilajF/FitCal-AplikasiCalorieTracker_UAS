package com.example.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.MenuData

@Database(entities = [MenuData::class],
    version = 2, // Ubah versi ke versi yang lebih tinggi
    exportSchema = false)
abstract class MenuRoomDatabase : RoomDatabase() {

    abstract fun menuDao() : MenuDAO?

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
