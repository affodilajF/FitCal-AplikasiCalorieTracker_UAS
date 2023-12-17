package com.example.myapplication.view.menuAdmin.home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHomepageAdminBinding
import com.example.myapplication.view.menuAdmin.adminAddMenu.AdminAddMenuActivity
import com.example.myapplication.view.menuAdmin.adminUpdateMenu.AdminUpdateMenuActivity
import com.example.myapplication.view.menuUser.addMenu.addMenu.AddMenuActivity
import com.example.myapplication.view.menuUser.listMenu.ListMenuActivity
import com.example.myapplication.view.menuUser.listMenu.MenuAdapter

class HomeAdminFragment : Fragment() {

    private lateinit var binding : FragmentHomepageAdminBinding
    private lateinit var viewModel: HomeAdminViewModel
    private lateinit var adapterMenuItem : MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeAdminViewModel::class.java)

        binding = FragmentHomepageAdminBinding.inflate(inflater, container, false)
        val view = binding.root



//        rec view
        observeMenus()
        viewModel.getAllMenus()


        adapterMenuItem = MenuAdapter { menu ->
            val intent = Intent(requireContext(), AdminUpdateMenuActivity::class.java).apply {
                putExtra("menu object", menu)
            }
//            nanti ganti ke see menu oleh admin
            startActivity(intent)
        }

        binding.rvItemMenu.apply {
            adapter = adapterMenuItem
            layoutManager = LinearLayoutManager(requireContext())
        }


        binding.btnAdd.setOnClickListener {
            val intent = Intent(requireContext(), AdminAddMenuActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        return view


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun observeMenus(){
        viewModel.menuListLiveData.observe(viewLifecycleOwner){
                menus ->
            adapterMenuItem.updateData(menus)

        }
    }

}