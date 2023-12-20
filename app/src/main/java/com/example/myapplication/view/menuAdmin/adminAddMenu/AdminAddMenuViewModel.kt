package com.example.myapplication.view.menuAdmin.adminAddMenu

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.MenuAdminDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.firestore.Menu
import com.example.myapplication.data.model.room.MenuAdmin
import com.example.myapplication.util.Network
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AdminAddMenuViewModel(application: Application): AndroidViewModel(application) {

//    firestore
    private var firestore = FirebaseFirestore.getInstance()
    private val menuCollectionRef = firestore.collection("menus")

//    room db
    private lateinit var mMenuAdminDao : MenuAdminDAO
    private lateinit var executorService : ExecutorService

    fun initializeDBRoom(context: Context){
        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(context)
        mMenuAdminDao = db!!.menuAdminDao()!!
    }

    fun addMenu(menu : Menu, context: Context){
        val a = Network.isInternetAvailable(context)
        if(a){
            menuCollectionRef.add(menu)
                .addOnFailureListener {
                    Log.d("ListMenuActivity", "Error adding menu : ", it)
                }

        } else {
            val a = MenuAdmin(calAmount = menu.calAmount, name = menu.name, fatGram = menu.fatGram, proteinGram = menu.proteinGram, carbsGram = menu.carbsGram)
            val runnable = Runnable { mMenuAdminDao.insert(a) }
            executorService.execute(runnable)
        }
    }
}