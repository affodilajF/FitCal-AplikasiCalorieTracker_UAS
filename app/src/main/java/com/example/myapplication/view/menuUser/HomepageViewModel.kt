package com.example.myapplication.view.menuUser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.UserProfile
import com.example.myapplication.util.SharedPreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomepageViewModel(application: Application) : AndroidViewModel(application) {

//    akses ke db firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val sharedPreferencesHelper =
            SharedPreferencesHelper.getInstance(application.applicationContext)

    private var firestore = FirebaseFirestore.getInstance()
    private val userProfileCollectionRef = firestore.collection("userProfile")

    fun getUserId(): String {
        return auth.currentUser?.uid ?: ""
    }
    val userProfileListLiveData : MutableLiveData<List<UserProfile>> by lazy {
        MutableLiveData<List<UserProfile>>()
    }
    private var satuUser  : UserProfile = UserProfile()

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

                    sharedPreferencesHelper.saveUserId(satuUser.userIdAuth)
                    sharedPreferencesHelper.saveUsername(satuUser.userName)
                    sharedPreferencesHelper.saveCarbsGram(satuUser.carbsGram)
                    sharedPreferencesHelper.saveProteinGram(satuUser.proteinGram)
                    sharedPreferencesHelper.saveFatGram(satuUser.fatGram)
                    sharedPreferencesHelper.saveCurrentWeight(satuUser.currentWeight)
                    sharedPreferencesHelper.saveTargetedWeight(satuUser.targetedWeight)
                    sharedPreferencesHelper.saveUserPhone(satuUser.userPhone)
                    sharedPreferencesHelper.saveDietGoal(satuUser.dietGoal)
                    sharedPreferencesHelper.saveHeight(satuUser.height)
                    sharedPreferencesHelper.saveDayTargetedCalorie(satuUser.dayTargetedCalorie)
                }
            }
    }
}