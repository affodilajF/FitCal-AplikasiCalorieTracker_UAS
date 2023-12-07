package com.example.myapplication.view.menuUser.listMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityListMenuBinding

class ListMenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnBack.setOnClickListener{
                finish()
            }

        }




    }
}