package com.example.myapplication.data.model

import java.io.Serializable
import java.util.Date

data class MenuData(

//    stored in room

//    get from firebase auth
    val userId: String = "",
    val name: String = "",

    val calAmount: Double = 0.0,
    val fatGram: Double = 0.0,
    val carbsGram: Double = 0.0,
    val proteinGram: Double = 0.0,

    val servings: Double = 0.0,
    val date: Date = Date(),

    val category: String = ""

) : Serializable
