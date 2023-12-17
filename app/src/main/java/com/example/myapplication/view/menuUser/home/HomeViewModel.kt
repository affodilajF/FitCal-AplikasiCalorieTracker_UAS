package com.example.myapplication.view.menuUser.home

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.room.MenuData
import com.example.myapplication.data.model.firestore.UserProfile
import com.example.myapplication.util.SharedPreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mMenuDao : MenuDAO
    private lateinit var executorService : ExecutorService

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var firestore = FirebaseFirestore.getInstance()
    private val userProfileCollectionRef = firestore.collection("userProfile")

    fun getUserId(): String {
        return auth.currentUser?.uid ?: ""
    }

    val userProfileListLiveData : MutableLiveData<List<UserProfile>> by lazy {
        MutableLiveData<List<UserProfile>>()
    }
    private var satuUser  : UserProfile = UserProfile()

    //    sharedpref
    private val sharedPreferencesHelper =
        SharedPreferencesHelper.getInstance(application.applicationContext)

    fun initDBRoom(context: Context){
        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(context)
        mMenuDao = db!!.menuDao()!!
    }

    fun getUserDataByUserId() {
        userProfileCollectionRef
            .whereEqualTo("userIdAuth", getUserId())
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    Log.d("MainActivity", "Error listening for budget changes", error)
                    return@addSnapshotListener
                }

                val data = arrayListOf<UserProfile>()
                snapshots?.forEach { documentReference ->
                    data.add(
                        UserProfile(
                            documentReference.id,
                            userIdAuth = documentReference.get("userIdAuth").toString(),
                            userName = documentReference.get("userName").toString(),
                            userPhone = documentReference.get("userPhone").toString(),
                            dayTargetedCalorie = documentReference.get("dayTargetedCalorie").toString(),
                            dietGoal = documentReference.get("dietGoal").toString(),
                            currentWeight = documentReference.get("currentWeight").toString(),
                            targetedWeight = documentReference.get("targetedWeight").toString(),
                            height = documentReference.get("height").toString(),
                            fatGram = documentReference.get("fatGram").toString(),
                            carbsGram = documentReference.get("carbsGram").toString(),
                            proteinGram = documentReference.get("proteinGram").toString()
                        )
                    )
                }
                if (data.isNotEmpty()) {
                    userProfileListLiveData.postValue(data)
                    satuUser = data[0]

                    sharedPreferencesHelper.saveUsername(satuUser.userName)
                    sharedPreferencesHelper.saveUserPhone(satuUser.userPhone)
                }
            }
    }


    fun getAllLiveDataByCategory(filter : String, date : String): LiveData<List<MenuData>> {
        return mMenuDao.allMenusByCategory(sharedPreferencesHelper.getUserId().toString(), filter, date)
    }


    fun getAmountCalAllLiveData(date : String): LiveData<Int> {
        return mMenuDao.getTotalAmountCal(sharedPreferencesHelper.getUserId().toString(), date )
    }

    fun getAmountGramAllCarbsLiveData(date : String): LiveData<Double> {
        return mMenuDao.getTotalServingTimesCarbs(sharedPreferencesHelper.getUserId().toString(), date )
    }

    fun getAmountGramAllProteinLiveData(date : String): LiveData<Double> {
        return mMenuDao.getTotalServingTimesProtein(sharedPreferencesHelper.getUserId().toString(), date )
    }


}