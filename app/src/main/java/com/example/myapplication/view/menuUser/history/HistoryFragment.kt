package com.example.myapplication.view.menuUser.history

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.Menu
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.view.menuUser.addMenu.addMenu.AddMenuActivity
import com.example.myapplication.view.menuUser.addMenu.addMenuCustom.AddCustomMenuActivity
import com.example.myapplication.view.menuUser.addMenu.updateMenu.UpdateMenuActivity
import com.example.myapplication.view.menuUser.listMenu.ListMenuActivity
import com.example.myapplication.view.menuUser.listMenu.MenuAdapter
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding

    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapterMenuItem : MenuDataAdapter
//
    private lateinit var mMenuDao : MenuDAO
    private lateinit var executorService : ExecutorService

    private var allMenusLiveData : LiveData<List<MenuData>>? = null


    private var dateTrigger : Int = 0
    private var txtDateFilter = ""

    private var selectedCategoryFilter = "All"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(requireContext())
        mMenuDao = db!!.menuDao()!!


        with(binding){
            txtDateNow.text = viewModel.getFormattedDate(viewModel.getTodayDate())
            dateTrigger = 0
            txtDateFilter = viewModel.getFormattedDate(viewModel.getTodayDate())

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radioall -> {
                        getAllMenus("userid")
                        selectedCategoryFilter = "userid"
                    }
                    R.id.radiobreakfast-> {
                        getAllMenus("Breakfast")
                        selectedCategoryFilter = "Breakfast"
                    }
                    R.id.radiodinner->{
                        getAllMenus("Dinner")
                        selectedCategoryFilter = "Dinner"
                    }
                    R.id.radiolunch->{
                        getAllMenus("Lunch")
                        selectedCategoryFilter = "Lunch"
                    }
                    R.id.radiosnack->{
                        getAllMenus("Snack")
                        selectedCategoryFilter = "Snack"

                    }
                }

            }


            adapterMenuItem = MenuDataAdapter { menu: MenuData ->
                val intent = Intent(requireContext(), UpdateMenuActivity::class.java).apply {
                    putExtra("menu object", menu)
                }
                requireContext().startActivity(intent)
            }

            with(binding){
                rvItemMenu.apply {
                    adapter = adapterMenuItem
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }


            imageButtonBack.setOnClickListener{
                dateTrigger = dateTrigger - 1
                val a = viewModel.getExactDateDays(dateTrigger)
                txtDateNow.text = viewModel.getFormattedDate(a)
                txtDateFilter = viewModel.getFormattedDate(a)
                getAllMenus(selectedCategoryFilter)
                getAllMenus("userid")


            }


            imageButtonNext.setOnClickListener{
                dateTrigger = dateTrigger + 1
                val a = viewModel.getExactDateDays(dateTrigger)
                txtDateNow.text = viewModel.getFormattedDate(a)
                txtDateFilter = viewModel.getFormattedDate(a)
                getAllMenus(selectedCategoryFilter)
                getAllMenus("userid")
            }

            btnSearch.setOnClickListener{
                startActivity(Intent(requireContext(), ListMenuActivity::class.java))
            }
            btnCustomAdd.setOnClickListener{
                startActivity(Intent(requireContext(), AddCustomMenuActivity::class.java))
            }
        }

        getAllMenus("userid")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    fun getAllMenus(filter : String){
//        by user id
        if(filter == "userid"){
            allMenusLiveData = viewModel.getAllLiveDataByUserId(mMenuDao, txtDateFilter)
        } else if (filter == "Breakfast" || filter == "Lunch" || filter == "Dinner" || filter =="Snack" ){
            allMenusLiveData = viewModel.getAllLiveDataByCategory(mMenuDao, filter, txtDateFilter)
        }


        allMenusLiveData?.observe(viewLifecycleOwner, Observer { menus ->
            menus?.let {
                adapterMenuItem.updateData(menus)
            }
        })
    }


}