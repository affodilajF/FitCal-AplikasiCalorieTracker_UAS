package com.example.myapplication.view.menuUser.addMenu.addMenuCustom

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myapplication.R
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.databinding.ActivityAddCustomMenuBinding
import com.example.myapplication.view.menuUser.addMenu.addMenu.AddMenuViewModel
import java.util.Calendar
import java.util.Date

class AddCustomMenuActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding : ActivityAddCustomMenuBinding
    private val viewModel: AddCustomMenuViewModel by viewModels()

    private lateinit var mealcategoryarray : Array<String>
    private var selectedmealcategory = ""
    private lateinit var selectedDate : Date
    private lateinit var menuFix : MenuData

    private var temporaryCarbs = ""
    private var temporaryProtein= ""
    private var temporaryFat = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCustomMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedDate = viewModel.getTodayDate()

        mealcategoryarray = resources.getStringArray(R.array.mealcategory)
        val adapterMealCategory = ArrayAdapter(this@AddCustomMenuActivity, android.R.layout.simple_spinner_item, mealcategoryarray)
        adapterMealCategory.setDropDownViewResource(
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item
        )

        with(binding){
            btnBack.setOnClickListener {
                finish()
            }

            txtDatePlaceholder.text = viewModel.getFormattedDate(selectedDate)

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

//            math kalori
            inputCarbs.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    calculatedCalCarbs.text = viewModel.getCalCarbs(s.toString()) + " cal"
                    temporaryCarbs = s.toString()

//                    Toast.makeText(this@AddCustomMenuActivity, temporaryCarbs, Toast.LENGTH_SHORT).show()

                    calculatedAllCal1serving.text = viewModel.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat) + " calories in 1 serving"

                }
                override fun afterTextChanged(s: Editable?) {
                }
            })

            inputProtein.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    calculatedCalProtein.text = viewModel.getCalProtein(s.toString())  + " cal"
                    temporaryProtein = s.toString()
                    calculatedAllCal1serving.text = viewModel.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat) + " calories in 1 serving"


                }
                override fun afterTextChanged(s: Editable?) {
                }
            })

            inputFat.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    calculatedCalFat.text = viewModel.getCalFat(s.toString())  + " cal"
                    temporaryFat = s.toString()
                    calculatedAllCal1serving.text = viewModel.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat) + " calories in 1 serving"
                }
                override fun afterTextChanged(s: Editable?) {
                }
            })


            editTextServingsNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    txtTotalCalCalculated.text = viewModel.getTotalCal(editTextServingsNumber.text.toString(), viewModel.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat))
                }
                override fun afterTextChanged(s: Editable?) {

                }
            })










        }



    }

    override fun onDateSet(view: android.widget.DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        selectedDate = calendar.time

        binding.txtDatePlaceholder.text = viewModel.getFormattedDate(selectedDate)

//        Toast.makeText(this@AddMenuActivity, selectedDate.toString(), Toast.LENGTH_SHORT).show()

    }

}