package com.example.myapplication.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.ActivityAuthBinding
import com.google.android.material.tabs.TabLayoutMediator



class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding

    lateinit var viewPager2: ViewPager2
    lateinit var mediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
//        if(viewModel.checkLoginStatus()){
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        with(binding){
            viewPager2 = viewPagerAuth

            viewPagerAuth.adapter = AuthTabAdapter(supportFragmentManager, this@AuthActivity.lifecycle)

            mediator = TabLayoutMediator(tabLayout, viewPagerAuth)
            { tab, position->
                when(position){
                    0->tab.text = "Register"
                    1->tab.text = "Login"
                }
            }
            mediator.attach()
        }




    }
}