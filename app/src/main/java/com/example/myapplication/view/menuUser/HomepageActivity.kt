package com.example.myapplication.view.menuUser

import android.app.DatePickerDialog
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomepageBinding


class HomepageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

























        with(binding){
            val navController = findNavController(R.id.nav_host_fragment)
            bottomNavigationViews.setupWithNavController(navController)
        }
    }
}