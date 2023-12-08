package com.example.myapplication.view.personalData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityPersonalDataBinding
import com.example.myapplication.view.menuUser.HomepageActivity

import java.util.Date

class PersonalDataActivity : AppCompatActivity() {
    private var temporaryCarbs = ""
    private var temporaryProtein= ""
    private var temporaryFat = ""

    private lateinit var binding : ActivityPersonalDataBinding
    private lateinit var viewModel: PersonalDataViewModel

    private var selectedGender = ""

    private lateinit var dietgoalcategoryarray : Array<String>
    private var selecteddietgoalcategory = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PersonalDataViewModel::class.java)

        dietgoalcategoryarray = resources.getStringArray(R.array.dietgoal)
        val adapterMealCategory = ArrayAdapter(this@PersonalDataActivity, android.R.layout.simple_spinner_item, dietgoalcategoryarray)
        adapterMealCategory.setDropDownViewResource(
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item
        )

        with(binding){

//            responsiveness perhitungan kalori
            //            math kalori
            inputCarbs.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    temporaryCarbs = s.toString()
                    txtTotalCalCalculated.text = viewModel.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat)


                }
                override fun afterTextChanged(s: Editable?) {
                }
            })

            inputProtein.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    temporaryProtein = s.toString()
                    txtTotalCalCalculated.text = viewModel.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat)


                }
                override fun afterTextChanged(s: Editable?) {
                }
            })

            inputFat.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    temporaryFat = s.toString()
                    txtTotalCalCalculated.text = viewModel.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat)
                }
                override fun afterTextChanged(s: Editable?) {
                }
            })





//            validate data
//            get user id
//            store data to firebase firestore
            spinnerCategory.adapter = adapterMealCategory
            spinnerCategory.onItemSelectedListener =
                object  : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id : Long) {
                        selecteddietgoalcategory = dietgoalcategoryarray[position]
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }

            btnDone.setOnClickListener {
                val intent = Intent(this@PersonalDataActivity, HomepageActivity::class.java)
                startActivity(intent)
                finish()

                val carbsGram = inputCarbs.text.toString()
//                viewModel.saveCarbsGram(carbsGram)

                val proteinGram = inputProtein.text.toString()
//                viewModel.saveProteinGram(proteinGram)
                val fatGram = inputFat.text.toString()
//                viewModel.saveFatGram(fatGram)

                val calDayTarget = txtTotalCalCalculated.text.toString()
//                viewModel.saveDayTargetedCalorie(calDayTarget)
                val dietGoal = selecteddietgoalcategory
//                viewModel.saveDietGoal(dietGoal)
                val cWeight = inputCweight.text.toString()
//                viewModel.saveCurrentWeight1(cWeight)
                val tWeight = inputTweight.text.toString()
//                viewModel.saveTargetedWeight(tWeight)
                val height = inputHeight.text.toString()
//                viewModel.saveHeight(height)

//                if(carbsGram.isBlank() || proteinGram.isBlank())

                viewModel.createUserProfile(p1DayTarget = calDayTarget, p2DietGoal = dietGoal, p3cWeight = cWeight,
                    p4tWeight = tWeight, p5heght = height,  p7carbs = carbsGram,
                    p8protein = proteinGram, p9fat = fatGram)

            }



        }

    }

}