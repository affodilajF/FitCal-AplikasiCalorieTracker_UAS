package com.example.myapplication.data.model.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "menuAdmin01_table")

data class MenuAdmin (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,

    val name: String = "",

    val calAmount: String = "0",
    val fatGram: String = "",
    val carbsGram: String = "",
    val proteinGram: String = "",

    var urlPhoto : String = "https://i.pinimg.com/736x/a9/0c/a2/a90ca2296a70e51dede826b88c354065.jpg"


) : Serializable