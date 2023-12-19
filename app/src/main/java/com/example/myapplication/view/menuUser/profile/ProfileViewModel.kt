package com.example.myapplication.view.menuUser.profile

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.firestore.UserProfile
import com.example.myapplication.util.SharedPreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferencesHelper = SharedPreferencesHelper.getInstance(application.applicationContext)

    fun logout(){
        sharedPreferencesHelper.clear()
    }

//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firestore = FirebaseFirestore.getInstance()
    private val userProfileCollectionRef = firestore.collection("userProfile")

    val userProfileListLiveData : MutableLiveData<List<UserProfile>> by lazy {
        MutableLiveData<List<UserProfile>>()
    }
    private var satuUser  : UserProfile = UserProfile()

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
//                            fatGram = documentReference.get("fatGram").toString(),
//                            carbsGram = documentReference.get("carbsGram").toString(),
//                            proteinGram = documentReference.get("proteinGram").toString()

                        )
                    )
                }
                if (data.isNotEmpty()) {
                    userProfileListLiveData.postValue(data)
                    satuUser = data[0]


                }
            }
    }


}