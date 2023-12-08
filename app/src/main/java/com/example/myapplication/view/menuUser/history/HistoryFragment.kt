package com.example.myapplication.view.menuUser.history

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.model.Menu
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.view.menuUser.addMenu.addMenu.AddMenuActivity
import com.example.myapplication.view.menuUser.addMenu.addMenuCustom.AddCustomMenuActivity
import com.example.myapplication.view.menuUser.addMenu.listMenu.ListMenuActivity
import com.example.myapplication.view.menuUser.addMenu.listMenu.MenuAdapter

class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding

    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapterMenuItem : MenuDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        with(binding){
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio2 -> {

                    }
                }
            }

//            btnAdd.setOnClickListener {
//                startActivity(Intent(requireContext(), ListMenuActivity::class.java))
//
//            }

            adapterMenuItem = MenuDataAdapter { menu: MenuData ->
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    putExtra("menu object", menu)
                }
                requireContext().startActivity(intent)
            }

            with(binding){
//                btnBack.setOnClickListener{
//                    finish()
//                }

                rvItemMenu.apply {
                    adapter = adapterMenuItem
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }



            btnSearch.setOnClickListener{
                startActivity(Intent(requireContext(), ListMenuActivity::class.java))
            }
            btnCustomAdd.setOnClickListener{
                startActivity(Intent(requireContext(), AddCustomMenuActivity::class.java))
            }


        }



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

}