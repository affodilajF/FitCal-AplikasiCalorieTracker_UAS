package com.example.myapplication.view.menuUser.addMenu.addMenuCustom

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myapplication.R
import com.example.myapplication.data.model.room.MenuData
import com.example.myapplication.databinding.ActivityAddCustomMenuBinding
import com.example.myapplication.util.CalorieCalculator
import com.example.myapplication.util.DateUtils
import com.example.myapplication.util.Formatter
import java.util.Calendar
import java.util.Date

class AddCustomMenuActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding : ActivityAddCustomMenuBinding
    private val viewModel: AddCustomMenuViewModel by viewModels()

    private lateinit var mealcategoryarray : Array<String>
    private var selectedmealcategory = ""
    private lateinit var selectedDate : Date


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCustomMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedDate = DateUtils.getTodayDate()
        viewModel.initializeDBRoom(this)
        setupTextWatchers()

        mealcategoryarray = resources.getStringArray(R.array.mealcategory)
        val adapterMealCategory = ArrayAdapter(this@AddCustomMenuActivity, android.R.layout.simple_spinner_item, mealcategoryarray)
        adapterMealCategory.setDropDownViewResource(
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item
        )


        with(binding){
            btnDone.setOnClickListener {
//                userid
//                foodname
                val a : String = txtName.text.toString()
//               calAmount
                val b : Int = Formatter.formattedInt(txtTotalCalCalculated.text.toString())
//                carbsGram
                val c : Int =inputCarbs.text.toString().toIntOrNull() ?: 0
//                fatGram
                val d: Int = inputFat.text.toString().toIntOrNull() ?: 0
//                proteinGram
                val e : Int = inputProtein.text.toString().toIntOrNull() ?: 0
//                servings
                val f : Double = Formatter.formattedDouble(editTextServingsNumber.text.toString())
//                date
                val g : String = DateUtils.getFormattedDate(selectedDate)
//                category
                val h = selectedmealcategory
//                cal100gr
                val i : Int = CalorieCalculator.getTotalCal100(c , d, e)

                val k = txtUrl.text.toString()

                val menuFix = MenuData(userId = viewModel.getUserId(),
                    name = a, calAmount = b, carbsGram = c, fatGram = d, proteinGram = e,
                    servings = f, date = g, category = h, calAmount100 = i)

                if(k != ""){
                    menuFix.urlPhoto = k
                }


                viewModel.insertRoom(menuFix)
                finish()
            }
        }


        with(binding){
            btnBack.setOnClickListener {
                finish()
            }

            txtDatePlaceholder.text = DateUtils.getFormattedDate(selectedDate)

            spinnerCategory.adapter = adapterMealCategory
            spinnerCategory.onItemSelectedListener =
                object  : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id : Long) {
                        selectedmealcategory = mealcategoryarray[position]
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }

            btnSelectDate.setOnClickListener {
                val datePicker = com.example.myapplication.view.menuUser.dialog.DatePickerDialog()
                datePicker.show(supportFragmentManager, "datePicker")

            }
        }



    }

    override fun onDateSet(view: android.widget.DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        selectedDate = calendar.time

        binding.txtDatePlaceholder.text = DateUtils.getFormattedDate(selectedDate)
    }

    private fun setupTextWatchers() {
        val textWatchers = arrayOf(
            binding.inputCarbs to binding.calculatedCalCarbs,
            binding.inputProtein to binding.calculatedCalProtein,
            binding.inputFat to binding.calculatedCalFat
        )

        textWatchers.forEach { (input, calculatedCal) ->
            input.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val result = when (calculatedCal) {
                        binding.calculatedCalCarbs -> CalorieCalculator.getCalCarbs(s.toString())
                        binding.calculatedCalProtein -> CalorieCalculator.getCalProtein(s.toString())
                        binding.calculatedCalFat -> CalorieCalculator.getCalFat(s.toString())
                        else -> 0
                    }
                    calculatedCal.text = "$result cal"
                    updateCalculatedAllCalories()
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
        binding.editTextServingsNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val result = CalorieCalculator.getTotalCal(
                    s.toString(),
                    CalorieCalculator.getCalculatedAllCalories(
                        binding.inputCarbs.text.toString(),
                        binding.inputProtein.text.toString(),
                        binding.inputFat.text.toString()
                    )
                )
                binding.txtTotalCalCalculated.text = result
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateCalculatedAllCalories() {
        val carbs = binding.inputCarbs.text.toString()
        val protein = binding.inputProtein.text.toString()
        val fat = binding.inputFat.text.toString()

        val result = CalorieCalculator.getCalculatedAllCalories(carbs, protein, fat)
        binding.calculatedAllCal1serving.text = "$result calories in 100 gr"
    }


}