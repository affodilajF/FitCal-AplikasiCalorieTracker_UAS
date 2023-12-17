package com.example.myapplication.data.model.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menuAdmin01_table")

data class MenuAdmin (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,

    val name: String = "",

    val calAmount: String = "0.0",
    val fatGram: String = "",
    val carbsGram: String = "",
    val proteinGram: String = "",

    )