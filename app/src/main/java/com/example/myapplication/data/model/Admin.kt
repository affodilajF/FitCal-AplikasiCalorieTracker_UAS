package com.example.myapplication.data.model

import com.google.firebase.firestore.Exclude

class Admin (
    @set:Exclude
    @get:Exclude
    @Exclude
    var id: String = "",

    val adminID : String = ""
    )