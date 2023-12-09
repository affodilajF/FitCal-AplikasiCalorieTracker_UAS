package com.example.myapplication.view.menuUser.personalData

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Menu
import com.example.myapplication.data.model.UserProfile
import com.example.myapplication.util.SharedPreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PersonalDataViewModel(application: Application) : AndroidViewModel(application) {

//    firebase
    private var firestore = FirebaseFirestore.getInstance()
    private val profileCollectionRef = firestore.collection("userProfile")

//    sharedpref
    private val sharedPreferencesHelper =
        SharedPreferencesHelper.getInstance(application.applicationContext)

//    create userProfile in firebase
    fun createUserProfile(p1DayTarget : String, p2DietGoal : String, p3cWeight : String, p4tWeight : String, p5heght : String,
    p7carbs : String, p8protein : String, p9fat : String
    ){

        val userIdAuth = sharedPreferencesHelper.getUserId()
        val userName = sharedPreferencesHelper.getUsername()
        val userPhone = sharedPreferencesHelper.getUserPhone()

        val dayTargetedCalorie= p1DayTarget
        val dietGoal = p2DietGoal
        val currentWeight = p3cWeight
        val targetedWeight = p4tWeight
        val height = p5heght


        val carbsGr = p7carbs
        val proteGr = p8protein
        val fatGr = p9fat

        val userprofile = if (userIdAuth != null && userName != null && userPhone != null) {
            UserProfile(userIdAuth = userIdAuth, userName =  userName, userPhone = userPhone, dayTargetedCalorie = dayTargetedCalorie,
                dietGoal = dietGoal, currentWeight = currentWeight, targetedWeight = targetedWeight, height = height,
                 carbsGram = carbsGr, proteinGram = proteGr, fatGram = fatGr
                )
        } else {
            null
        }

        if (userprofile != null) {
            profileCollectionRef.add(userprofile)
                .addOnFailureListener {
                    Log.d("ListMenuActivity", "Error adding menu : ", it)
                }
        }
    }


    fun getCalCarbs(gram : String?) : String {
        val a = gram?.toDoubleOrNull() ?: 0.0
        return String.format("%.0f", a * 4)
    }

    fun getCalProtein(gram: String?): String {
        val a = gram?.toDoubleOrNull() ?: 0.0
        return String.format("%.0f", a * 4)
    }

    fun getCalFat(gram: String?): String {
        val a = gram?.toDoubleOrNull() ?: 0.0
        return String.format("%.0f", a * 9)
    }

    fun getCalculatedAllCalories(gram : String?, gram2 : String?, gram3 : String?) : String{
        val a = getCalCarbs(gram).toDouble()
        val b = getCalProtein(gram2).toDouble()
        val c = getCalFat(gram3).toDouble()

        return (a + b + c).toString()

    }


}