package com.example.myapplication.data.model.firestore

import com.google.firebase.firestore.Exclude
import java.io.Serializable


data class Menu(

//    stored in firebase
    @set:Exclude @get:Exclude @Exclude var id: String = "",

    val name: String = "",

    val calAmount: String = "0",
    val fatGram: String = "",
    val carbsGram: String = "",
    val proteinGram: String = "",

) : Serializable

