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
import com.example.myapplication.data.model.firestore.Menu
import com.example.myapplication.data.model.room.MenuData
import com.example.myapplication.databinding.ActivityAddMenuBinding
import com.example.myapplication.util.CalorieCalculator
import com.example.myapplication.util.DateUtils
import java.util.Calendar
import java.util.Date

class AddMenuActivity : AppCompatActivity(),  DatePickerDialog.OnDateSetListener {

    private lateinit var binding : ActivityAddMenuBinding
    private val viewModel: AddMenuViewModel by viewModels()

    private lateinit var mealcategoryarray : Array<String>
    private var selectedmealcategory = ""
    private lateinit var selectedDate : Date


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initializeDBRoom(this)


        selectedDate = DateUtils.getTodayDate()

        mealcategoryarray = resources.getStringArray(R.array.mealcategory)
        val adapterMealCategory = ArrayAdapter(this@AddMenuActivity, android.R.layout.simple_spinner_item, mealcategoryarray)
        adapterMealCategory.setDropDownViewResource(
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item
        )

        with(binding){
            txtDatePlaceholder.text = DateUtils.getFormattedDate(selectedDate)

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

            txtCalCarbs.text = CalorieCalculator.getCalCarbs(carbs).toString() + " cal"
            txtCalFat.text = CalorieCalculator.getCalFat(fat).toString() + " cal"
            txtCalProtein.text = CalorieCalculator.getCalProtein(protein).toString() + " cal"

            txtGrCarbs.text = carbs + " gr"
            txtGrFat.text = fat + " gr"
            txtGrProtein.text = protein + " gr"
            txtCalOneserving.text = calories + " cal"

            btnDone.setOnClickListener {
                val menuFix = MenuData( userId = viewModel.getUserId() , name = name, calAmount = txtTotalCalCalculated.text.toString().toInt(),  fatGram =  fat.toInt(), carbsGram = carbs.toInt(), proteinGram = protein.toInt(), servings = editTextServingsNumber.text.toString().toDouble(),
                    date = DateUtils.getFormattedDate(selectedDate), category = selectedmealcategory, calAmount100 = calories.toInt() )
                viewModel.insertMenu(menuFix)
                finish()
            }

            editTextServingsNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    txtTotalCalCalculated.text = CalorieCalculator.getTotalCal(calories, s.toString())

                    calculatedCalCarbs.text = CalorieCalculator.getCalCarbsOnUserServing(carbs, s.toString())
                    calculatedCalFat.text = CalorieCalculator.getCalFatOnUserServing(carbs, s.toString())
                    calculatedCalProtein.text = CalorieCalculator.getCalProteinOnUserServing(carbs, s.toString())
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