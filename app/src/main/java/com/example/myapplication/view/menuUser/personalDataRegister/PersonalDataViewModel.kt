package com.example.myapplication.view.menuUser.personalDataRegister

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.data.model.firestore.UserProfile
import com.example.myapplication.util.SharedPreferencesHelper
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
        val gemail = sharedPreferencesHelper.getUserGmail()

        val dayTargetedCalorie= p1DayTarget
        val dietGoal = p2DietGoal
        val currentWeight = p3cWeight
        val targetedWeight = p4tWeight
        val height = p5heght


        val carbsGr = p7carbs
        val proteGr = p8protein
        val fatGr = p9fat

        val userprofile = if (userIdAuth != null && userName != null && userPhone != null && gemail != null) {
            UserProfile(userIdAuth = userIdAuth, userName =  userName, userPhone = userPhone, dayTargetedCalorie = dayTargetedCalorie,
                dietGoal = dietGoal, currentWeight = currentWeight, targetedWeight = targetedWeight, height = height,
                 carbsGram = carbsGr, proteinGram = proteGr, fatGram = fatGr,
                email = gemail
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




}