package com.example.myapplication.view.menuUser.home

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.database.MenuDataDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.room.MenuData
import com.example.myapplication.data.model.firestore.UserProfile
import com.example.myapplication.util.SharedPreferencesHelper
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mMenuDataDao : MenuDataDAO
    private lateinit var executorService : ExecutorService

    private var firestore = FirebaseFirestore.getInstance()
    private val userProfileCollectionRef = firestore.collection("userProfile")

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
        mMenuDataDao = db!!.menuDao()!!
    }

    fun getUserDataByUserId() {
        userProfileCollectionRef
            .whereEqualTo("userIdAuth", sharedPreferencesHelper.getUserId())
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
                            email = documentReference.get("email").toString(),


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

                }
            }
    }


    fun getAllLiveDataByCategory(filter : String, date : String): LiveData<List<MenuData>> {
        return mMenuDataDao.allMenusByCategory(sharedPreferencesHelper.getUserId().toString(), filter, date)
    }


    fun getAmountCalAllLiveData(date : String): LiveData<Int> {
        return mMenuDataDao.getTotalAmountCal(sharedPreferencesHelper.getUserId().toString(), date )
    }

    fun getAmountGramAllCarbsLiveData(date : String): LiveData<Double> {
        return mMenuDataDao.getTotalServingTimesCarbs(sharedPreferencesHelper.getUserId().toString(), date )
    }



    fun getAmountGramAllProteinLiveData(date : String): LiveData<Double> {
        return mMenuDataDao.getTotalServingTimesProtein(sharedPreferencesHelper.getUserId().toString(), date )
    }

    fun getAmountGramAllFatLiveData(date : String): LiveData<Double> {
        return mMenuDataDao.getTotalServingTimesFat(sharedPreferencesHelper.getUserId().toString(), date )
    }


}