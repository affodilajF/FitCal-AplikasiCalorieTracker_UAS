package com.example.myapplication.view.menuUser.home

import android.animation.ValueAnimator
import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myapplication.data.database.MenuDAO
import com.example.myapplication.data.database.MenuRoomDatabase
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.view.auth.AuthViewModel
import com.example.myapplication.view.menuUser.HomepageActivity
import com.example.myapplication.view.menuUser.HomepageViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomeFragment : Fragment()  {

    private var allMenusLiveData : LiveData<List<MenuData>>? = null
    private var allMenusLiveData1 : LiveData<List<MenuData>>? = null
    private var allMenusLiveData2 : LiveData<List<MenuData>>? = null
    private var allMenusLiveData3 : LiveData<List<MenuData>>? = null

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding : FragmentHomeBinding

    private var dateTrigger : Int = 0
    private var txtDateFilter = "yiha"

    private lateinit var progressAnimator: ValueAnimator

    private var targetedCalByDay : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.initDBRoom(requireContext())
        viewModel.getUserDataByUserId()

        observeData()

        with(binding){

            txtDateNow.text = viewModel.getFormattedDate(viewModel.getTodayDate())
            dateTrigger = 0
            txtDateFilter = viewModel.getFormattedDate(viewModel.getTodayDate())


            imageButtonBack.setOnClickListener{
                dateTrigger -= 1
                val a = viewModel.getExactDateDays(dateTrigger)
                txtDateNow.text = viewModel.getFormattedDate(a)
                txtDateFilter = viewModel.getFormattedDate(a)
                getAllMenus()
                getRemainingCal(targetedCalByDay)
            }
            imageButtonNext.setOnClickListener{
                dateTrigger += 1
                val a = viewModel.getExactDateDays(dateTrigger)
                txtDateNow.text = viewModel.getFormattedDate(a)
                txtDateFilter = viewModel.getFormattedDate(a)
                getAllMenus()
                getRemainingCal(targetedCalByDay)


            }
        }
        getAllMenus()
        return view
    }


        private fun observeData(){
            with(binding){
                viewModel.userProfileListLiveData.observe(viewLifecycleOwner, Observer {myData
                    ->
                    val userObject = myData[0]

                    txtDitTarget.text = userObject.dietGoal

                    txtUsername.text = userObject.userName
                    cweight.text = userObject.currentWeight
                    tweight.text = userObject.targetedWeight
                    txtTargetcal.text = userObject.dayTargetedCalorie + " cal / day"

                    carbsTarget.text = "25 of " + userObject.carbsGram + "gr"
                    proteinTarget.text = "67 of " + userObject.proteinGram+ " gr"
                    fatTarget.text = "69 of " + userObject.fatGram+ " gr"

                    targetedCalByDay = (userObject.dayTargetedCalorie).toDouble().toInt()

                    getRemainingCal(targetedCalByDay)
                })
            }
        }

        private fun getRemainingCal(dayTargetedCal : Int){
            val liveDataIntTotalAchievedCal: LiveData<Int> = viewModel.getAmountCalAllLiveDataByUserId(txtDateFilter)
            liveDataIntTotalAchievedCal.observe(viewLifecycleOwner) { intValue ->

                if(intValue != null){
                    val remainingcal  = viewModel.getRemainingCal(dayTargetedCal, intValue)
                    val progressInd = viewModel.getPercentProgress(dayTargetedCal, intValue)
                    binding.textRemainingCal.text = remainingcal
                    animateProgressBar(progressInd)

                } else {
                    binding.textRemainingCal.text = targetedCalByDay.toString()
                    animateProgressBar(0)
                }
            }
        }


        private fun animateProgressBar(progressInd : Int){
                progressAnimator = ValueAnimator.ofInt(0, 100)
                progressAnimator.duration = 400

                progressAnimator.addUpdateListener { animator ->
                    val animatedValue = animator.animatedValue as Int
                    binding.progressCircularIndicator.progress = animatedValue
                }

                progressAnimator.setIntValues(binding.progressCircularIndicator.progress, progressInd)
                progressAnimator.start()


        }

        private fun getAllMenus(){

                allMenusLiveData = viewModel.getAllLiveDataByCategory("Breakfast", txtDateFilter)
                allMenusLiveData?.observe(viewLifecycleOwner, Observer { menus ->
                    menus?.let {
                        with(binding){
                            txtCountBreakfast.text = (menus.size).toString()
                        }
                    }
                })

                allMenusLiveData1 = viewModel.getAllLiveDataByCategory("Snack", txtDateFilter)
                allMenusLiveData1?.observe(viewLifecycleOwner, Observer { menus ->
                    menus?.let {
                        with(binding){
                            txtCountSnack.text = (menus.size).toString()
                        }
                    }
                })

                allMenusLiveData2 = viewModel.getAllLiveDataByCategory("Lunch", txtDateFilter)
                allMenusLiveData2?.observe(viewLifecycleOwner, Observer { menus ->
                    menus?.let {
                        with(binding){
                            txtCountLunch.text = (menus.size).toString()
                        }
                    }
                })

                allMenusLiveData3 = viewModel.getAllLiveDataByCategory("Dinner", txtDateFilter)
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