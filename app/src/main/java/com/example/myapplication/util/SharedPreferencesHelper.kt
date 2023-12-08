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


        private const val KEY_DAY_TARGETED_CALORIE = "1"
        private const val KEY_DIET_GOAL = "2"
        private const val KEY_CURRENT_WEIGHT: String = "3"
        private const val KEY_TARGETED_WEIGHT: String = "4"
        private const val KEY_HEIGHT: String = "5"


        private const val KEY_FAT_GRAM: String = "7"
        private const val KEY_CARBS_GRAM: String = "8"
        private const val KEY_PROTEIN_GRAM: String = "9"


//        private const val KEY_PASSWORD = "password"

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

//    -----------------------
    fun saveDayTargetedCalorie(dayTargetedCalorie: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_DAY_TARGETED_CALORIE, dayTargetedCalorie)
        editor.apply()
    }

        fun getDayTargetedCalorie(): String? {
            return sharedPreferences.getString(KEY_DAY_TARGETED_CALORIE, null)
        }

    fun saveDietGoal(dietGoal: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_DIET_GOAL, dietGoal)
        editor.apply()
    }

    fun getDietGoal(): String? {
        return sharedPreferences.getString(KEY_DIET_GOAL, null)
    }

    fun saveCurrentWeight(currentWeight: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_CURRENT_WEIGHT, currentWeight)
        editor.apply()
    }

    fun getCurrentWeight(): String? {
        return sharedPreferences.getString(KEY_CURRENT_WEIGHT, null)
    }

    fun saveTargetedWeight(targetedWeight: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_TARGETED_WEIGHT, targetedWeight)
        editor.apply()
    }

    fun getTargetedWeight(): String? {
        return sharedPreferences.getString(KEY_TARGETED_WEIGHT, null)
    }

    fun saveHeight(height: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_HEIGHT, height)
        editor.apply()
    }

    fun getHeight(): String? {
        return sharedPreferences.getString(KEY_HEIGHT, null)
    }


    fun saveFatGram(fatGram: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_FAT_GRAM, fatGram)
        editor.apply()
    }

    fun getFatGram(): String? {
        return sharedPreferences.getString(KEY_FAT_GRAM, null)
    }

    fun saveCarbsGram(carbsGram: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_CARBS_GRAM, carbsGram)
        editor.apply()
    }

    fun getCarbsGram(): String? {
        return sharedPreferences.getString(KEY_CARBS_GRAM, null)
    }

    fun saveProteinGram(proteinGram: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_PROTEIN_GRAM, proteinGram)
        editor.apply()
    }

    fun getProteinGram(): String? {
        return sharedPreferences.getString(KEY_PROTEIN_GRAM, null)
    }



    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }



















//    fun savePassword(password: String) {
//        val editor = sharedPreferences.edit()
//        editor.putString(KEY_PASSWORD, password)
//        editor.apply()
//    }
//
//
//    fun getPassword(): String? {
//        return sharedPreferences.getString(KEY_PASSWORD, null)
//    }



}
