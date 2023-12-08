package com.example.myapplication.view.menuUser.addMenu.listMenu

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
    private lateinit var adapterMenuItem : MenuAdapter

    private val viewModel: ListMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Mendefinisikan observer untuk LiveData dari ViewModel
        observeMenus()
        viewModel.getAllMenus()


//        temporary, buat nambahin data menu
//        val dummymenu = Menu (name = "Fried Chicken", calAmount = "345", fatGram = "39", carbsGram = "28.2", proteinGram = "65.6")
//        viewModel.addMenu(dummymenu)
//
//        val dummymenu2 = Menu (name = "Ice Cream", calAmount = "289", fatGram = "25.7", carbsGram = "23.9", proteinGram = "29.6")
//        viewModel.addMenu(dummymenu2)
//
//        val dummymenu3 = Menu (name = "Banana", calAmount = "129", fatGram = "15", carbsGram = "20.2", proteinGram = "15.8")
//        viewModel.addMenu(dummymenu3)



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