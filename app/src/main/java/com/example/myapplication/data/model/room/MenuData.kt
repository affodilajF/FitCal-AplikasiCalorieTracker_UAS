package com.example.myapplication.data.model.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "menu01_table")
data class MenuData(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,

//    get from firebase auth
    var userId: String = "",
    val name: String = "",

//    ini cal amount dari berapa serving yang diinputkan user
    val calAmount: Int = 0,

    var urlPhoto : String = "https://i.pinimg.com/736x/a9/0c/a2/a90ca2296a70e51dede826b88c354065.jpg",

//    ini fat crab protein per 100gr (sesuai dengan inputan awal / database firebase)
    val calAmount100 : Int = 0,
    val fatGram: Int = 0,
    val carbsGram: Int = 0,
    val proteinGram: Int = 0,

    val servings: Double = 0.0,
    val date: String = "",

    val category: String = ""

) : Serializable
