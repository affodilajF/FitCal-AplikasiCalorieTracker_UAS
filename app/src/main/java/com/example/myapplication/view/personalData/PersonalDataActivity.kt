package com.example.myapplication.view.personalData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityPersonalDataBinding
import com.example.myapplication.view.menuUser.HomepageActivity

class PersonalDataActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPersonalDataBinding
    private lateinit var viewModel: PersonalDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PersonalDataViewModel::class.java)

        with(binding){

//            validate data
//            get user id
//            store data to firebase firestore

            btnGetStarted.setOnClickListener {
                val intent = Intent(this@PersonalDataActivity, HomepageActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}