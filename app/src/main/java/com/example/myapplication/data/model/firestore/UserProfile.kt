package com.example.myapplication.data.model.firestore

import com.google.firebase.firestore.Exclude

data class UserProfile(

//    stored in firebase
//    invoked when user regist n login
//    collectionname = userProfile

    @set:Exclude @get:Exclude @Exclude var id: String = "",

    val userIdAuth : String = "",
    val userName : String = "",
    val userPhone : String = "",

    val dayTargetedCalorie: String ="",
    val dietGoal : String = "",
    val currentWeight : String = "",
    val targetedWeight : String = "",
    val height : String = "",


//    targetnya, nilainya diisi pas awal regist
    val fatGram: String = "",
    val carbsGram: String = "",
    val proteinGram: String = "",



    )
