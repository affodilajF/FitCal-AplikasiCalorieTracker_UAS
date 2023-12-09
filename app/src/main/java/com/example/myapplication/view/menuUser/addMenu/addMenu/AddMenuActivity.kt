package com.example.myapplication.view.menuUser.addMenu.addMenu

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.myapplication.R
import com.example.myapplication.data.model.Menu
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.databinding.ActivityAddMenuBinding
import java.util.Calendar
import java.util.Date

class AddMenuActivity : AppCompatActivity(),  DatePickerDialog.OnDateSetListener {

    private lateinit var binding : ActivityAddMenuBinding
    private lateinit var mealcategoryarray : Array<String>
    private var selectedmealcategory = ""
    private lateinit var selectedDate : Date
    private lateinit var menuFix : MenuData

    private val viewModel: AddMenuViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedDate = viewModel.getTodayDate()

        mealcategoryarray = resources.getStringArray(R.array.mealcategory)
        val adapterMealCategory = ArrayAdapter(this@AddMenuActivity, android.R.layout.simple_spinner_item, mealcategoryarray)
        adapterMealCategory.setDropDownViewResource(
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item
        )

        viewModel.initializeDBRoom(this)

        with(binding){
            txtDatePlaceholder.text = viewModel.getFormattedDate(selectedDate)

            btnBack.setOnClickListener{
                finish()
            }

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

            val menu = intent.getSerializableExtra("menu object") as Menu
            val name = menu.name
            val calories = menu.calAmount
            val carbs = menu.carbsGram
            val protein = menu.proteinGram
            val fat = menu.fatGram


            txtName.text = menu.name

            txtCalCarbs.text = viewModel.getCalCarbs(carbs) + " cal"
            txtCalFat.text = viewModel.getCalFat(fat) + " cal"
            txtCalProtein.text = viewModel.getCalProtein(protein) + " cal"

            txtGrCarbs.text = carbs + " gr"
            txtGrFat.text = fat + " gr"
            txtGrProtein.text = protein + " gr"
            txtCalOneserving.text = calories + " cal"


//            nanti invoke viewmodel yang terhubung dengan room

            btnDone.setOnClickListener {
                menuFix = MenuData( name = name, calAmount = txtTotalCalCalculated.text.toString().toDouble(),  fatGram =  fat.toDouble(), carbsGram = carbs.toDouble(), proteinGram = protein.toDouble(), servings = editTextServingsNumber.text.toString().toDouble(),
                    date = viewModel.getFormattedDate(selectedDate), category = selectedmealcategory )
                viewModel.insertRoom(menuFix)
                finish()
            }

            editTextServingsNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    txtTotalCalCalculated.text = viewModel.getTotalCal(calories, s.toString())

                    calculatedCalCarbs.text = ((viewModel.getCalCarbs(carbs)).toDouble()*((s.toString()).toDoubleOrNull() ?: 0.0)).toString()
                    calculatedCalFat.text = ((viewModel.getCalFat(fat)).toDouble()*((s.toString()).toDoubleOrNull() ?: 0.0)).toString()
                    calculatedCalProtein.text = ((viewModel.getCalProtein(protein)).toDouble()*((s.toString()).toDoubleOrNull() ?: 0.0)).toString()
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

    }
}