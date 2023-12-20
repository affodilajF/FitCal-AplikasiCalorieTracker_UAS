package com.example.myapplication.view.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel

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

    private var firestore = FirebaseFirestore.getInstance()

    fun fetchUserRoleFromFirestore(onResult: (String) -> Unit) {
        val userId = auth.currentUser?.uid
        userId?.let { uid ->
            firestore.collection("userProfile")
                .whereEqualTo("userIdAuth", uid)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val role = document.getString("role")
                        if (role != null) {
                            onResult(role)
                            saveUserRoleSharePrefs(role)
                        }
                    }
                }
                .addOnFailureListener {
                }
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


    fun loginUser(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(true, "Login Successes")
                        sharedPreferencesHelper.setLoggedIn(true)
                        sharedPreferencesHelper.saveUserId(getUserId())
                    } else {
                        onResult(false, "Login Failed: ${task.exception?.message ?: "Unknown error"}")
                    }
                }
        } catch (e: Exception) {
            onResult(false, "Login Failed: ${e.message ?: "Unknown error"}")
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

    private fun saveUserRoleSharePrefs(user : String){
        sharedPreferencesHelper.saveUserRole(user)
    }

    fun saveUserEmailSharePrefs(user : String){
        sharedPreferencesHelper.saveUserGmail(user)
    }


}