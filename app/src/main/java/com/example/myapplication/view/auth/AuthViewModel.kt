package com.example.myapplication.view.auth

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.myapplication.util.SharedPreferencesHelper
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val sharedPreferencesHelper = SharedPreferencesHelper.getInstance(application.applicationContext)

    fun registerUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true)
                } else {
                    onResult(false)
                }
            }
    }

    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sharedPreferencesHelper.setLoggedIn(true)
                    onResult(true)
                } else {
                    onResult(false)
                }
            }
    }

    fun checkLoginStatus() : Boolean{
        return sharedPreferencesHelper.isLoggedIn()
    }



//    private fun saveLoginStatus(isLoggedIn: Boolean) {
//        val sharedPreferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putBoolean("isLoggedIn", isLoggedIn)
//        editor.apply()
//    }



}