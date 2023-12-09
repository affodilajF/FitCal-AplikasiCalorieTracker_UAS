package com.example.myapplication.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "menu1_table")
data class MenuData(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,

//    stored in room

//    get from firebase auth

    var userId: String = "",
    val name: String = "",

//    ini cal amount dari berapa serving yang diinputkan user
    val calAmount: Double = 0.0,

//    ini fat crab protein per 100gr (sesuai dengan inputan awal / database firebase)
    val fatGram: Double = 0.0,
    val carbsGram: Double = 0.0,
    val proteinGram: Double = 0.0,

    val servings: Double = 0.0,
    val date: String = "",

    val category: String = ""

) : Serializable
