package com.example.myapplication.data.model.firestore

import com.google.firebase.firestore.Exclude

data class UserProfile(

//    stored in firebase
//    filled when user registering
//    collection = userProfile
    @set:Exclude @get:Exclude @Exclude var id: String = "",

    val userIdAuth : String = "",
    val userName : String = "",
    val userPhone : String = "",
    val email : String = "",

    val dayTargetedCalorie: String ="",
    val dietGoal : String = "",
    val currentWeight : String = "",
    val targetedWeight : String = "",
    val height : String = "",

//    target, nilainya diisi pas awal register
    val fatGram: String = "",
    val carbsGram: String = "",
    val proteinGram: String = "",

    )
