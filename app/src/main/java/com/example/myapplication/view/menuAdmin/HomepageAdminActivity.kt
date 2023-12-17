package com.example.myapplication.view.menuAdmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAdminHomepageBinding

class HomepageAdminActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_homepage_admin)

        binding = ActivityAdminHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(binding){
            val navControllerAdmin = findNavController(R.id.nav_host_fragment_admin)
            bottomNavigationViewsAdmin.setupWithNavController(navControllerAdmin)
        }
    }
}