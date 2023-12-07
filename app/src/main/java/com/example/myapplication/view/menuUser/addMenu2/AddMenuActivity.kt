package com.example.myapplication.view.menuUser.addMenu2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.ActivityAddMenuBinding
import com.google.android.material.tabs.TabLayoutMediator

class AddMenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddMenuBinding

    lateinit var viewPager2: ViewPager2
    lateinit var mediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        with(binding){
//            viewPager2 = viewPagerMenu
//
//            viewPagerMenu.adapter = MenuTabAdapter(supportFragmentManager, this@AddMenuActivity.lifecycle)
//
//            mediator = TabLayoutMediator(tabLayout, viewPagerMenu)
//            { tab, position->
//                when(position){
//                    0->tab.text = "Search"
//                    1->tab.text = "Custom"
//                }
//            }
//            mediator.attach()
//
//            btnBack.setOnClickListener{
//                finish()
//            }
//
//        }


    }
}