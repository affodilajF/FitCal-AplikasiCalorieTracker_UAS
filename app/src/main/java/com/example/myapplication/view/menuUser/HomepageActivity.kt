package com.example.myapplication.view.menuUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomepageBinding
import com.example.myapplication.util.SharedPreferencesHelper

class HomepageActivity : AppCompatActivity() {
    private lateinit var homepageViewModel: HomepageViewModel

    private lateinit var binding : ActivityHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homepageViewModel = ViewModelProvider(this).get(HomepageViewModel::class.java)

        with(binding){
            val navController = findNavController(R.id.nav_host_fragment)
            bottomNavigationViews.setupWithNavController(navController)
        }

    }
}