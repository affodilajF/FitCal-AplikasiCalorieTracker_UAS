package com.example.myapplication.view.menuUser.listMenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.model.Menu
import com.example.myapplication.databinding.ActivityListMenuBinding
import com.example.myapplication.view.menuUser.addMenu.addMenu.AddMenuActivity

class ListMenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListMenuBinding
    private val viewModel: ListMenuViewModel by viewModels()

    private lateinit var adapterMenuItem : MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeMenus()
        viewModel.getAllMenus()

        adapterMenuItem = MenuAdapter {
                menu: Menu ->
            val intent = Intent(this, AddMenuActivity::class.java).apply {
                putExtra("menu object", menu)
            }
            startActivity(intent)
        }

        with(binding){
            btnBack.setOnClickListener{
                finish()
            }

            rvItemMenu.apply {
                adapter = adapterMenuItem
                layoutManager = LinearLayoutManager(this@ListMenuActivity)
            }
        }
    }

    private fun observeMenus(){
        viewModel.menuListLiveData.observe(this){
                menus ->
            adapterMenuItem.updateData(menus)

        }
    }
}