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
import com.example.myapplication.data.model.MenuData
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

    private var temporaryCarbs = "0.0"
    private var temporaryProtein= "0.0"
    private var temporaryFat = "0.0"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCustomMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedDate = DateUtils.getTodayDate()

        viewModel.initializeDBRoom(this)

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
                Toast.makeText(this@AddCustomMenuActivity, a, Toast.LENGTH_SHORT).show()

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
                val i : Int = (c*4)+(d*9)+(e*4)

                val menuFix = MenuData(userId = viewModel.getUserId(),
                    name = a, calAmount = b, carbsGram = c, fatGram = d, proteinGram = e,
                    servings = f, date = g, category = h, calAmount100 = i)
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

//            math kalori
            inputCarbs.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    calculatedCalCarbs.text = CalorieCalculator.getCalCarbs(s.toString()).toString() + " cal"
                    temporaryCarbs = s.toString()

                    calculatedAllCal1serving.text = CalorieCalculator.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat) + " calories in 1 serving"

                }
                override fun afterTextChanged(s: Editable?) {
                }
            })

            inputProtein.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    calculatedCalProtein.text = CalorieCalculator.getCalProtein(s.toString()).toString()  + " cal"
                    temporaryProtein = s.toString()
                    calculatedAllCal1serving.text = CalorieCalculator.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat) + " calories in 1 serving"


                }
                override fun afterTextChanged(s: Editable?) {
                }
            })

            inputFat.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    calculatedCalFat.text = CalorieCalculator.getCalFat(s.toString()).toString()  + " cal"
                    temporaryFat = s.toString()
                    calculatedAllCal1serving.text = CalorieCalculator.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat) + " calories in 1 serving"
                }
                override fun afterTextChanged(s: Editable?) {
                }
            })


            editTextServingsNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    txtTotalCalCalculated.text = CalorieCalculator.getTotalCal(editTextServingsNumber.text.toString(), CalorieCalculator.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat))
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

        binding.txtDatePlaceholder.text = DateUtils.getFormattedDate(selectedDate)
    }




}