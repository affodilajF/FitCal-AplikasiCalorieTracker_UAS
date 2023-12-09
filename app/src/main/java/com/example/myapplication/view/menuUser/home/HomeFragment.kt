package com.example.myapplication.view.menuUser.home

import android.animation.ValueAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomeFragment : Fragment() {

    private lateinit var mMenuDao : MenuDAO
    private lateinit var executorService : ExecutorService

    private var allMenusLiveData : LiveData<List<MenuData>>? = null
    private var allMenusLiveData1 : LiveData<List<MenuData>>? = null
    private var allMenusLiveData2 : LiveData<List<MenuData>>? = null
    private var allMenusLiveData3 : LiveData<List<MenuData>>? = null

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding : FragmentHomeBinding

    private var dateTrigger : Int = 0
    private var txtDateFilter = "yiha"

    private lateinit var progressAnimator: ValueAnimator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        executorService = Executors.newSingleThreadExecutor()
        val db = MenuRoomDatabase.getDatabase(requireContext())
        mMenuDao = db!!.menuDao()!!

        with(binding){

            txtDateNow.text = viewModel.getFormattedDate(viewModel.getTodayDate())
            dateTrigger = 0
            txtDateFilter = viewModel.getFormattedDate(viewModel.getTodayDate())

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
                txtDateNow.text = viewModel.getFormattedDate(a)
                txtDateFilter = viewModel.getFormattedDate(a)
                getAllMenus()

            }
            imageButtonNext.setOnClickListener{
                dateTrigger = dateTrigger + 1
                val a = viewModel.getExactDateDays(dateTrigger)
                txtDateNow.text = viewModel.getFormattedDate(a)
                txtDateFilter = viewModel.getFormattedDate(a)
                getAllMenus()


            }
        }
        getAllMenus()
        return view
    }


        fun animateProgressBar(progressInd : Int){
                progressAnimator = ValueAnimator.ofInt(0, 100)
                progressAnimator.duration = 400

                progressAnimator.addUpdateListener { animator ->
                    val animatedValue = animator.animatedValue as Int
                    binding.progressCircularIndicator.progress = animatedValue
                }

                progressAnimator.setIntValues(binding.progressCircularIndicator.progress, progressInd)
                progressAnimator.start()


        }

        fun getAllMenus(){

//            get remaining calorie
            val liveDataDoubleTotalAchievedCal: LiveData<Double> = viewModel.getAmountCalAllLiveDataByUserId(mMenuDao, txtDateFilter)
            liveDataDoubleTotalAchievedCal.observe(viewLifecycleOwner) { doubleValue ->

                if(doubleValue != null){
                    val remainingcal  = viewModel.getRemainingCal(doubleValue)
                    val progressInd = viewModel.getPercentProgress(doubleValue)

                    binding.textRemainingCal.text = remainingcal
                    animateProgressBar(progressInd)
                } else {
                    binding.textRemainingCal.text = viewModel.getAmountCal()
                    animateProgressBar(0)

                }
            }

                allMenusLiveData = viewModel.getAllLiveDataByCategory(mMenuDao, "Breakfast", txtDateFilter)
                allMenusLiveData?.observe(viewLifecycleOwner, Observer { menus ->
                    menus?.let {
                        with(binding){
                            txtCountBreakfast.text = (menus.size).toString()
                        }
                    }
                })

                allMenusLiveData1 = viewModel.getAllLiveDataByCategory(mMenuDao, "Snack", txtDateFilter)
                allMenusLiveData1?.observe(viewLifecycleOwner, Observer { menus ->
                    menus?.let {
                        with(binding){
                            txtCountSnack.text = (menus.size).toString()
                        }
                    }
                })

                allMenusLiveData2 = viewModel.getAllLiveDataByCategory(mMenuDao, "Lunch", txtDateFilter)
                allMenusLiveData2?.observe(viewLifecycleOwner, Observer { menus ->
                    menus?.let {
                        with(binding){
                            txtCountLunch.text = (menus.size).toString()
                        }
                    }
                })

                allMenusLiveData3 = viewModel.getAllLiveDataByCategory(mMenuDao, "Dinner", txtDateFilter)
                allMenusLiveData3?.observe(viewLifecycleOwner, Observer { menus ->
                    menus?.let {
                        with(binding){
                            txtCountDinner.text = (menus.size).toString()
                        }
                    }
                })

        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}