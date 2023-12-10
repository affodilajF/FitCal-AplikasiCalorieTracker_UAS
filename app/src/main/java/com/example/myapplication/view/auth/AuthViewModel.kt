package com.example.myapplication.view.auth

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Admin
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

////    firestore
////    private var firestore = FirebaseFirestore.getInstance()
////    private val adminCollectionRef = firestore.collection("admins")
//    private val adminListLiveData : MutableLiveData<List<Admin>> by lazy {
//        MutableLiveData<List<Admin>>()
//    }

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


}


