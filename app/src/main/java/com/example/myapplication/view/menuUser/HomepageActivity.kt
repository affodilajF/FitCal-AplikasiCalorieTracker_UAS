package com.example.myapplication.view.menuUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomepageBinding


class HomepageActivity : AppCompatActivity() {
    private lateinit var viewModelActivity: HomepageViewModel

    private lateinit var binding : ActivityHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModelActivity = ViewModelProvider(this).get(HomepageViewModel::class.java)
        viewModelActivity.getUserDataByUserId()

        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            val navController = findNavController(R.id.nav_host_fragment)
            bottomNavigationViews.setupWithNavController(navController)
        }

    }


}