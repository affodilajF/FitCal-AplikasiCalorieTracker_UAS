package com.example.myapplication.view.menuUser.history

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.model.room.MenuData
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.util.DateUtils
import com.example.myapplication.view.menuUser.addMenu.addMenuCustom.AddCustomMenuActivity
import com.example.myapplication.view.menuUser.addMenu.updateMenu.UpdateMenuActivity
import com.example.myapplication.view.menuUser.listMenu.ListMenuActivity

class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding
    private lateinit var viewModel: HistoryViewModel

    private lateinit var adapterMenuItem : MenuDataAdapter

    private var allMenusLiveData : LiveData<List<MenuData>>? = null
    private var dateTrigger : Int = 0
    private var txtDateFilter = ""
    private var selectedCategoryFilter = "All"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.initDBRoom(requireContext())



        with(binding){
            txtDateNow.text = DateUtils.getFormattedDate(DateUtils.getTodayDate())
            dateTrigger = 0
            txtDateFilter = DateUtils.getFormattedDate(DateUtils.getTodayDate())

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radioall -> {
                        getAllMenus("All")
                        selectedCategoryFilter = "All"
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
                    putExtra("MenuData object", menu)
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
                dateTrigger -= 1
                val a = DateUtils.getExactDateDays(dateTrigger)
                txtDateNow.text = DateUtils.getFormattedDate(a)
                txtDateFilter = DateUtils.getFormattedDate(a)
                getAllMenus(selectedCategoryFilter)
            }

            imageButtonNext.setOnClickListener{
                dateTrigger += 1
                val a = DateUtils.getExactDateDays(dateTrigger)
                txtDateNow.text = DateUtils.getFormattedDate(a)
                txtDateFilter = DateUtils.getFormattedDate(a)
                getAllMenus(selectedCategoryFilter)
            }

            btnSearch.setOnClickListener{
//                startActivity(Intent(requireContext(), ListMenuActivity::class.java))
                val intent = Intent(requireContext(), ListMenuActivity::class.java)
                startActivity(intent)
                activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            btnCustomAdd.setOnClickListener{
//                startActivity(Intent(requireContext(), AddCustomMenuActivity::class.java))
                val intent = Intent(requireContext(), AddCustomMenuActivity::class.java)
                startActivity(intent)
                activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

//                val options = ActivityOptions.makeSceneTransitionAnimation(requireActivity())
//                requireContext().startActivity(intent, options.toBundle())
            }
        }

        getAllMenus("All")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    private fun getAllMenus(filter : String){

        allMenusLiveData = viewModel.getAllLiveDataByCategory(filter, txtDateFilter)

        allMenusLiveData?.observe(viewLifecycleOwner, Observer { menus ->
            menus?.let {
                adapterMenuItem.updateData(menus)
            }
        })
    }


}