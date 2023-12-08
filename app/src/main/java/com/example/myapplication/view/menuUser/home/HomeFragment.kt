package com.example.myapplication.view.menuUser.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.view.menuUser.HomepageViewModel

class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel


    private lateinit var binding : FragmentHomeBinding



    private var dateTrigger : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        with(binding){

            txtDateNow.text = viewModel.formattedDate(viewModel.getTodayDate())
            dateTrigger = 0

            txtUsername.text = viewModel.getUsn()
            cweight.text = viewModel.getCweight()
            tweight.text = viewModel.getTWeight()
            txtTargetcal.text = viewModel.getAmountCal() + " cal / day"

            carbsTarget.text = "25 of " + viewModel.getCarbsTarget() + "gr"
            proteinTarget.text = "67 of " + viewModel.getProteinTarget() + " gr"
            fatTarget.text = "69 of " + viewModel.getFatTarget() + " gr"


            imageButtonBack.setOnClickListener{
                dateTrigger = dateTrigger - 1
                val a = viewModel.getExactDateDays(dateTrigger)
                txtDateNow.text = viewModel.formattedDate(a)

            }


            imageButtonNext.setOnClickListener{
                dateTrigger = dateTrigger + 1
                val a = viewModel.getExactDateDays(dateTrigger)
                txtDateNow.text = viewModel.formattedDate(a)

            }
        }



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        sharedViewModel.getUserDataByUserId()
    }

}