package com.example.myapplication.view.auth

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.myapplication.util.SharedPreferencesHelper
import com.example.myapplication.view.menuAdmin.HomepageAdminActivity
import com.example.myapplication.view.menuUser.HomepageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val sharedPreferencesHelper =
        SharedPreferencesHelper.getInstance(application.applicationContext)

////    firestore

    private var firestore = FirebaseFirestore.getInstance()

    private val _userRole = MutableLiveData<String?>()
    val userRole: MutableLiveData<String?>
        get() = _userRole

    fun fetchUserRoleFromFirestore() {
        val userId = auth.currentUser?.uid

        userId?.let { uid ->
            firestore.collection("userProfile")
                .whereEqualTo("userIdAuth", uid)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val role = document.getString("role")
                        _userRole.value = role
                    }
                }
                .addOnFailureListener {
                }
        }
    }

    fun navigateBasedOnRole(): Class<out Any>? {
        val role = _userRole.value
        return if (role == "admin") {
            saveUserRoleSharePrefs("admin")
            HomepageAdminActivity::class.java
        } else {
            saveUserRoleSharePrefs("user")
            HomepageActivity::class.java
        }
    }


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

    fun saveUserRoleSharePrefs(user : String){
        sharedPreferencesHelper.saveUserRole(user)
    }


}