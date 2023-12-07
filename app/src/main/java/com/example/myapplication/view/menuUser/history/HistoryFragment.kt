package com.example.myapplication.view.menuUser.history

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.view.menuUser.addMenu.listMenu.ListMenuActivity
import com.example.myapplication.view.menuUser.addMenu2.AddMenuActivity

class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding

    private lateinit var viewModel: HistoryViewModel

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

            btnSearch.setOnClickListener{
                startActivity(Intent(requireContext(), ListMenuActivity::class.java))
            }


        }



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

}