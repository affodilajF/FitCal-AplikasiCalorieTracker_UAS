package com.example.myapplication.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "authAppPrefs"

        private const val KEY_USER_ID = "userIdFromFirebase"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"

        private const val KEY_USERNAME = "username"
        private const val KEY_PHONE = "phone"
        private const val KEY_ROLE = "role"
        private const val KEY_EMAIL = "gmail"


        @Volatile
        private var instance: SharedPreferencesHelper? = null

        fun getInstance(context: Context): SharedPreferencesHelper {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferencesHelper(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun saveUserId(userId: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USER_ID, userId)
        editor.apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun saveUserRole(userId: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ROLE, userId)
        editor.apply()
    }

    fun getUserRole(): String? {
        return sharedPreferences.getString(KEY_ROLE, null)
    }


    fun saveUserGmail(userId: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, userId)
        editor.apply()
    }

    fun getUserGmail(): String? {
        return sharedPreferences.getString(KEY_EMAIL, null)
    }


    fun saveUserPhone(userPhone: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_PHONE, userPhone)
        editor.apply()
    }
    fun getUserPhone(): String? {
        return sharedPreferences.getString(KEY_PHONE, null)
    }

    fun saveUsername(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }
    fun getUsername(): String? {
        return sharedPreferences.getString(KEY_USERNAME, null)
    }


    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}
