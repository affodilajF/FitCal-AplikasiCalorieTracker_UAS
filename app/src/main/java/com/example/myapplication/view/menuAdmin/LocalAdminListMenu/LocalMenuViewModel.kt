package com.example.myapplication.view.menuAdmin.LocalAdminListMenu

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
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

class LocalMenuViewModel : ViewModel() {
//    room untuk crud
    private lateinit var mMenuAdminDao : MenuAdminDAO
    private lateinit var executorService : ExecutorService

//firestore untuk sync room ke firebase
    val firestore = FirebaseFirestore.getInstance()
    val menuCollectionRef = firestore.collection("menus")


    fun initializeDBRoom(context: Context){
        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(context)
        mMenuAdminDao = db!!.menuAdminDao()!!
    }

    fun getAllLiveData() :  LiveData<List<MenuAdmin>> {
        return mMenuAdminDao.allMenus
    }
//    update
//    delete

    fun syncRoomToFirestore(lifecycleOwner: LifecycleOwner, context: Context){
        val a = Network.isInternetAvailable(context)
        if(a){
                val menuAdminLiveData = mMenuAdminDao.allMenus
                menuAdminLiveData.observe(lifecycleOwner) { menuAdminList ->
                    viewModelScope.launch {
                        val menuList = menuAdminList.map { menuAdmin ->
                            Menu(
                                name = menuAdmin.name,
                                calAmount = menuAdmin.calAmount,
                                fatGram = menuAdmin.fatGram,
                                carbsGram = menuAdmin.carbsGram,
                                proteinGram = menuAdmin.proteinGram
                            )
                        }

                        menuList.forEach { menu ->
                            try {
                                menuCollectionRef.add(menu)

                            } catch (e: Exception) {

                            }
                        }
                    }
                }
                deleteAllMenuFromRoom()

        }
    }




    private fun deleteAllMenuFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            mMenuAdminDao.deleteAll()
        }
    }

}

