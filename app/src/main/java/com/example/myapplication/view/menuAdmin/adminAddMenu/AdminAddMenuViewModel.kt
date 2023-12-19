package com.example.myapplication.view.menuAdmin.adminAddMenu

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.MenuAdminDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.room.MenuAdmin
import com.example.myapplication.util.Network
import com.example.myapplication.util.NetworkUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AdminAddMenuViewModel : ViewModel() {

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


    private var unsyncedLiveData: LiveData<List<MenuAdmin>>? = null
    fun getData(){
        unsyncedLiveData = mMenuAdminDao.getUnsyncedData()
    }

//    private var unsyncedLiveData: LiveData<List<MenuAdmin>>? = null
//    private val syncedDataHashSet = HashSet<MenuAdmin>()
//
//    fun getData() {
//        val newData = mMenuAdminDao.getUnsyncedData().value ?: listOf()
//
//        val uniqueData = newData.filter { !syncedDataHashSet.contains(it) }
//
//        syncedDataHashSet.addAll(uniqueData)
//        unsyncedLiveData = MutableLiveData(syncedDataHashSet.toList())
//    }




    fun addMenu(menu : MenuAdmin, context: Context){
        if(Network.isInternetAvailable(context)){
            menuCollectionRef.add(menu)
                .addOnFailureListener {
                    Log.d("ListMenuActivity", "Error adding menu : ", it)
                }
        } else {
            val runnable = Runnable { mMenuAdminDao.insert(menu) }
            executorService.execute(runnable)
        }
    }


    fun syncToFirestore(context: Context) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            val unsyncedLiveData = mMenuAdminDao.getUnsyncedData()
            var aa = listOf<MenuAdmin>()

            unsyncedLiveData?.observeForever { unsyncedData ->
                aa = unsyncedData
            }


            aa.forEach { menu ->
                menuCollectionRef.add(menu)
                deleteMenuFromRoom(menu)
            }


        }
    }



//    fun syncToFirestore(context: Context) {
//        if (NetworkUtils.isNetworkAvailable(context)) {
//            unsyncedLiveData?.observeForever { unsyncedData ->
//                unsyncedData?.forEach { menu ->
//                    menuCollectionRef.add(menu)
//                    deleteMenuFromRoom(menu)
////                        .addOnCompleteListener { task ->
////                            if (task.isSuccessful) {
//////                                onResult(true)
////                            }
////                        }
////                        .addOnFailureListener { exception ->
////                            Log.d("MainActivity", "Error adding note : ", exception)
////                        }
////                        .addOnSuccessListener {
////                            deleteMenuFromRoom(menu)
//////                            Log.d(
//////                                "MainActivity",
//////                                "DocumentSnapshot added with ID: ${documentReference.id}"
//////                            )
////                        }
//                }
//            }
//        }
//    }


    private fun deleteMenuFromRoom(menu: MenuAdmin) {
        viewModelScope.launch(Dispatchers.IO) {
            mMenuAdminDao.delete(menu)
        }
    }


    }