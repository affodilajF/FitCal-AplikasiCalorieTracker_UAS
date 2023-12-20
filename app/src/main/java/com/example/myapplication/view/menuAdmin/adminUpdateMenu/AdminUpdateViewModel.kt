package com.example.myapplication.view.menuAdmin.adminUpdateMenu

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.database.MenuAdminDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.firestore.Menu
import com.example.myapplication.data.model.room.MenuAdmin
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AdminUpdateViewModel : ViewModel() {
    //    firestore
    private var firestore = FirebaseFirestore.getInstance()
    private val menuCollectionRef = firestore.collection("menus")

    fun deleteMenu(menu: Menu){
        menuCollectionRef.document(menu.id).delete()
            .addOnFailureListener {
                Log.d("MainActivity", "Error deleting budget : ", it)
            }
    }

    fun updateMenu(menu: Menu){
        menuCollectionRef.document(menu.id).set(menu)
            .addOnFailureListener {
                Log.d("MainActivity", "Error deleting budget : ", it)
            }
    }


}