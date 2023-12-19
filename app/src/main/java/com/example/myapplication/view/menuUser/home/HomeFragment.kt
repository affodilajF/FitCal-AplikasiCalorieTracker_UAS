package com.example.myapplication.view.menuUser.home

import android.animation.ValueAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myapplication.data.model.room.MenuData
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.util.CalorieCalculator
import com.example.myapplication.util.DateUtils

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
    private lateinit var progressAnimator1: ValueAnimator

    private var targetedCalByDay : Int = 0
    private var targetedGramCarbsByDay : Int = 0
    private var targetedGramProteinByDay : Int = 0
    private var targetedGramFatByDay : Int = 0


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

            txtDateNow.text = DateUtils.getFormattedDate(DateUtils.getTodayDate())
            dateTrigger = 0
            txtDateFilter = DateUtils.getFormattedDate(DateUtils.getTodayDate())


            imageButtonBack.setOnClickListener{
                dateTrigger -= 1
                val a = DateUtils.getExactDateDays(dateTrigger)
                txtDateNow.text = DateUtils.getFormattedDate(a)
                txtDateFilter = DateUtils.getFormattedDate(a)
                getAllMenus()
                getRemainingCal(targetedCalByDay)
            }
            imageButtonNext.setOnClickListener{
                dateTrigger += 1
                val a = DateUtils.getExactDateDays(dateTrigger)
                txtDateNow.text = DateUtils.getFormattedDate(a)
                txtDateFilter = DateUtils.getFormattedDate(a)
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
                    txtTargetcal.text = userObject.dayTargetedCalorie

                    carbsTarget.text = "0 of " + userObject.carbsGram + "gr"
                    proteinTarget.text = "0 of " + userObject.proteinGram+ " gr"
                    fatTarget.text = "0 of " + userObject.fatGram+ " gr"


//                    call total
                    targetedCalByDay = (userObject.dayTargetedCalorie).toDouble().toInt()
//                    call carbs
                    targetedGramCarbsByDay = (userObject.carbsGram).toDouble().toInt()
                    targetedGramProteinByDay = (userObject.proteinGram).toDouble().toInt()
                    targetedGramFatByDay = (userObject.fatGram).toDouble().toInt()

                    getRemainingCal(targetedCalByDay)

                })
            }
        }

        private fun getRemainingCal(dayTargetedCal : Int){
            val liveDataIntTotalAchievedCal: LiveData<Int> = viewModel.getAmountCalAllLiveData(txtDateFilter)
            liveDataIntTotalAchievedCal.observe(viewLifecycleOwner) { intValue ->

                if(intValue != null){
                    val remainingcal  = CalorieCalculator.getRemainingCal(dayTargetedCal, intValue)
                    val progressIndAllCal = CalorieCalculator.getPercentProgress(dayTargetedCal, intValue)

                    if(remainingcal <= 0.toString()){
                        binding.textRemainingCal.text = "0"
                    } else {
                        binding.textRemainingCal.text = remainingcal
                    }
                    animateProgressBar(progressIndAllCal)

                } else {
                    binding.textRemainingCal.text = targetedCalByDay.toString()
                    animateProgressBar(0)
                }
            }



//          carbs
            val liveDataDoubleAchievedCarbsGram : LiveData<Double> = viewModel.getAmountGramAllCarbsLiveData(txtDateFilter)
            liveDataDoubleAchievedCarbsGram.observe(viewLifecycleOwner) { dobValueCarbs ->

                if(dobValueCarbs != null){
                    val progressIndCarbs = CalorieCalculator.getPercentProgressEachComponentGram(targetedGramCarbsByDay, dobValueCarbs)
                    binding.carbsTarget.text = dobValueCarbs.toInt().toString() +" of " + targetedGramCarbsByDay + " gr"
                    animateProgressBarCarbs(progressIndCarbs)
                } else {
                    binding.carbsTarget.text = "0 of " + targetedGramCarbsByDay + " gr"
                    animateProgressBarCarbs(0)
                }
            }

//            protein
            val liveDataDoubleAchievedProteinGram : LiveData<Double> = viewModel.getAmountGramAllProteinLiveData(txtDateFilter)
            liveDataDoubleAchievedProteinGram.observe(viewLifecycleOwner) { dobValueProtein ->

                if(dobValueProtein != null){
                    val progressIndProtein = CalorieCalculator.getPercentProgressEachComponentGram(targetedGramProteinByDay, dobValueProtein)
                    binding.proteinTarget.text = dobValueProtein.toInt().toString() +" of " + targetedGramProteinByDay + " gr"
                    animateProgressBarProtein(progressIndProtein)
                } else {
                    binding.proteinTarget.text = "0 of " + targetedGramProteinByDay + " gr"
                    animateProgressBarProtein(0)
                }
            }

//            fat
            val liveDataDoubleAchievedFatGram : LiveData<Double> = viewModel.getAmountGramAllFatLiveData(txtDateFilter)
            liveDataDoubleAchievedFatGram.observe(viewLifecycleOwner) { dobValueFat ->

                if(dobValueFat != null){
                    val progressIndFat = CalorieCalculator.getPercentProgressEachComponentGram(targetedGramProteinByDay, dobValueFat)
                    binding.fatTarget.text = dobValueFat.toInt().toString() +" of " + targetedGramProteinByDay + " gr"
                    animateProgressBarFat(progressIndFat)
                } else {
                    binding.fatTarget.text = "0 of " + targetedGramProteinByDay + " gr"
                    animateProgressBarFat(0)
                }
            }





        }


        private fun animateProgressBar(progressInd : Int){
                progressAnimator = ValueAnimator.ofInt(0, 100)
                progressAnimator.duration = 400
                progressAnimator.addUpdateListener { animator ->
                    val animatedValue = animator.animatedValue as Int
                    binding.progressCircularIndicator.progress = animatedValue
                    binding.progressCircularIndicator2.progress = animatedValue

                }
                progressAnimator.setIntValues(binding.progressCircularIndicator.progress, progressInd)
                progressAnimator.setIntValues(binding.progressCircularIndicator2.progress, progressInd)
                progressAnimator.start()
        }

        private fun animateProgressBarCarbs(progressInd : Int){
            progressAnimator1 = ValueAnimator.ofInt(0, 100)
            progressAnimator1.duration = 400

            progressAnimator1.addUpdateListener { animator ->
                val animatedValue = animator.animatedValue as Int
                binding.linearProgressCarbs.progress = animatedValue
            }
                    progressAnimator1.setIntValues(binding.linearProgressCarbs.progress, progressInd)
            progressAnimator1.start()
        }

        private fun animateProgressBarProtein(progressInd : Int){
            progressAnimator1 = ValueAnimator.ofInt(0, 100)
            progressAnimator1.duration = 400

            progressAnimator1.addUpdateListener { animator ->
                val animatedValue = animator.animatedValue as Int
                binding.linearProgressProtein.progress = animatedValue
            }
            progressAnimator1.setIntValues(binding.linearProgressProtein.progress, progressInd)
            progressAnimator1.start()
        }

        private fun animateProgressBarFat(progressInd : Int){
            progressAnimator1 = ValueAnimator.ofInt(0, 100)
            progressAnimator1.duration = 400

            progressAnimator1.addUpdateListener { animator ->
                val animatedValue = animator.animatedValue as Int
                binding.linearProgressFat.progress = animatedValue
            }

            progressAnimator1.setIntValues(binding.linearProgressFat.progress, progressInd)
            progressAnimator1.start()
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