package com.example.myapplication.view.getStarted

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityWellcomingBinding
import com.example.myapplication.view.auth.AuthActivity
import com.example.myapplication.view.menuUser.HomepageActivity

class WellcomingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWellcomingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWellcomingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGetStarted.setOnClickListener {
            val intent1 = Intent(this, AuthActivity::class.java)
            startActivity(intent1)
            finish()
        }




    }
}