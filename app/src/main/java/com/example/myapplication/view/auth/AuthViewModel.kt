package com.example.myapplication.view.auth

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Menu
import com.example.myapplication.data.model.UserProfile
import com.example.myapplication.util.SharedPreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val sharedPreferencesHelper =
        SharedPreferencesHelper.getInstance(application.applicationContext)

//    firestore
    private var firestore = FirebaseFirestore.getInstance()

    val userProfileListLiveData : MutableLiveData<List<UserProfile>> by lazy {
        MutableLiveData<List<UserProfile>>()
    }
    private val userProfileCollectionRef = firestore.collection("userProfile")


    fun registerUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true)
                    sharedPreferencesHelper.setLoggedIn(true)
                } else {
                    onResult(false)
                }
            }
    }

    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true)
                    sharedPreferencesHelper.setLoggedIn(true)
                } else {
                    onResult(false)
                }
            }
    }


    fun getUserId(): String {
        return auth.currentUser?.uid ?: ""
    }
    fun saveUserIdSharePrefs(userid : String){
        sharedPreferencesHelper.saveUserId(userid)
    }
    fun saveUserNameSharePrefs(user : String){
        sharedPreferencesHelper.saveUsername(user)
    }
    fun saveUserPhoneSharePrefs(user : String){
        sharedPreferencesHelper.saveUserPhone(user)
    }

//    fungsi untuk update semua data untuk share pref

//    fun getUserDataByUserId() {
//        userProfileCollectionRef
//            .whereEqualTo("userIdAuth", getUserId())
//            .addSnapshotListener { snapshots, error ->
//                if (error != null) {
//                    Log.d("MainActivity", "Error listening for budget changes", error)
//                    return@addSnapshotListener
//                }
//
//                val data = arrayListOf<UserProfile>()
//                snapshots?.forEach { documentReference ->
//                    data.add(
//                        UserProfile(
//                            documentReference.id,
//                            userIdAuth = documentReference.get("userIdAuth").toString(),
//                            userName = documentReference.get("userName").toString(),
//                            userPhone = documentReference.get("userPhone").toString(),
//                            dayTargetedCalorie = documentReference.get("dayTargetedCalorie").toString(),
//                            dietGoal = documentReference.get("dietGoal").toString(),
//                            currentWeight = documentReference.get("currentWeight").toString(),
//                            targetedWeight = documentReference.get("targetedWeight").toString(),
//                            height = documentReference.get("height").toString(),
//                            fatGram = documentReference.get("fatGram").toString(),
//                            carbsGram = documentReference.get("carbsGram").toString(),
//                            proteinGram = documentReference.get("proteinGram").toString()
//                        )
//                    )
//                }
//                if (data.isNotEmpty()) {
//                    userProfileListLiveData.postValue(data)
//                    val satuUser = data[0]
//
//                    sharedPreferencesHelper.saveUsername(satuUser.userName)
//                    sharedPreferencesHelper.saveCarbsGram(satuUser.carbsGram)
//                    sharedPreferencesHelper.saveProteinGram(satuUser.proteinGram)
//                    sharedPreferencesHelper.saveFatGram(satuUser.fatGram)
//                    sharedPreferencesHelper.saveCurrentWeight(satuUser.currentWeight)
//                    sharedPreferencesHelper.saveTargetedWeight(satuUser.targetedWeight)
//                    sharedPreferencesHelper.saveUserPhone(satuUser.userPhone)
//                    sharedPreferencesHelper.saveDietGoal(satuUser.dietGoal)
//                    sharedPreferencesHelper.saveHeight(satuUser.height)
//                    sharedPreferencesHelper.saveDayTargetedCalorie(satuUser.dayTargetedCalorie)
//                }
//            }
//    }






    // Mendapatkan satu baris data berdasarkan userAuthId tertentu
//    fun getUserProfileByUserAuthId() {
//        userProfileCollectionRef
//            .whereEqualTo("userIdAuth", getUserId())
//            .get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    // Mendapatkan data dari dokumen yang sesuai
////                    cek = document.toObject(UserProfile::class.java)
//
//                    // Lakukan sesuatu dengan userProfile (misalnya, tampilkan, simpan, atau gunakan data tersebut)
//                }
//            }
//            .addOnFailureListener { exception ->
//                // Tangani kegagalan pengambilan data
//                Log.w("TAG", "Error getting documents: ", exception)
//            }
//    }








}


