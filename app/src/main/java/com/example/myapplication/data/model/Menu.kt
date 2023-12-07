package com.example.myapplication.data.model

import java.io.Serializable
import java.util.Date

data class Menu(

    @set:Exclude @get:Exclude @Exclude var id : String = "",


    val name : String = "",

    val calAmount : Double = 0.0,
    val fatGram : Double = 0.0,
    val carbsGram : Double = 0.0,
    val proteinsGram : Double = 0.0,

    val servings : Double = 0.0,
    val date : Date = Date(),

    val category : String = ""

    
) : Serializable

