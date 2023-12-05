package com.example.myapplication.view.menuUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.util.SharedPreferencesHelper

class HomepageActivity : AppCompatActivity() {
    private lateinit var homepageViewModel: HomepageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        homepageViewModel = ViewModelProvider(this).get(HomepageViewModel::class.java)

    }
}