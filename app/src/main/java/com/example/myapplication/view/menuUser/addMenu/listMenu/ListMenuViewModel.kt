package com.example.myapplication.view.menuUser.addMenu.listMenu

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Menu
import com.google.firebase.firestore.FirebaseFirestore

class ListMenuViewModel : ViewModel() {

    //    create properties to be used to acesses firebase
    private var firestore = FirebaseFirestore.getInstance()
    val menuListLiveData : MutableLiveData<List<Menu>> by lazy {
        MutableLiveData<List<Menu>>()
    }

    private val menuCollectionRef = firestore.collection("menus")


    fun getAllMenus() {
        menuCollectionRef.addSnapshotListener { snapshots, error ->

            if(error != null){
                Log.d("MainActivity", "Error listening for budget changes", error)
                return@addSnapshotListener
            }


            val menu = arrayListOf<Menu>()
            snapshots?.forEach{
                    documentReference ->
                menu.add(
                    Menu(documentReference.id,
                        documentReference.get("name").toString(),
                        documentReference.get("calAmount").toString(),
                        documentReference.get("fatGram").toString(),
                        documentReference.get("carbsGram").toString(),
                        documentReference.get("proteinGram").toString(),

                ))
            }

            if(menu != null){
                menuListLiveData.postValue(menu)
            }
        }
    }

    fun addMenu(menu : Menu){
        menuCollectionRef.add(menu)
            .addOnFailureListener {
                Log.d("ListMenuActivity", "Error adding menu : ", it)
            }

    }





}