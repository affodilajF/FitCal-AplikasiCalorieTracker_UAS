package com.example.myapplication.view.menuUser.addMenu.updateMenu

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
import com.example.myapplication.data.model.Menu
import com.example.myapplication.data.model.MenuData
import com.example.myapplication.databinding.ActivityAddMenuBinding
import com.example.myapplication.databinding.ActivityUpdateMenuBinding
import com.example.myapplication.view.menuUser.addMenu.addMenu.AddMenuViewModel
import java.util.Calendar
import java.util.Date


class UpdateMenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateMenuBinding
    private lateinit var menuFix : MenuData
    private val viewModel: UpdateMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initializeDBRoom(this)

        with(binding){

            btnBack.setOnClickListener{
                finish()
            }

            val menu = intent.getSerializableExtra("menu object") as MenuData
            val name = menu.name
            val calories = menu.calAmount
            val carbs = menu.carbsGram
            val protein = menu.proteinGram
            val fat = menu.fatGram
            val serving = menu.servings

            txtName.text = name
            txtCalCarbs.text = viewModel.getCalCarbs(carbs) + " cal"
            txtCalFat.text = viewModel.getCalFat(fat) + " cal"
            txtCalProtein.text = viewModel.getCalProtein(protein) + " cal"

            txtGrCarbs.text = carbs.toString() + " gr"
            txtGrFat.text = fat.toString() + " gr"
            txtGrProtein.text = protein.toString() + " gr"

            val totalCal = ((fat * 9) + (carbs * 4) + (protein * 4))
            val formattedTotalCal = String.format("%.0f", totalCal)
            txtCalOneserving.text = formattedTotalCal + " cal"


            val servingString = serving.toString()
            val editableServings = Editable.Factory.getInstance().newEditable(servingString)
            editTextServingsNumber.text = editableServings


            val result = ((((fat * 9) + (carbs * 4) + (protein * 4)) * serving))
            val formattedResult = String.format("%.0f", result)
            txtTotalCalCalculated.text = formattedResult + " cal"



            btnUpdate.setOnClickListener {
                val text = txtTotalCalCalculated.text.toString()
                val totalCalBaru = text.toDoubleOrNull() ?: 0.0
                val servBaru = editTextServingsNumber.text.toString()
                val servBaru2 = servBaru.toDoubleOrNull() ?: 0.0

                menuFix = MenuData(id = menu.id, userId = menu.userId, name=menu.name, fatGram = menu.fatGram, carbsGram = menu.carbsGram
                , proteinGram = menu.proteinGram, date = menu.date, category = menu.category,
                    calAmount = totalCalBaru, servings = servBaru2)
                viewModel.update(menuFix)
                finish()
            }

            btnDel.setOnClickListener {
                viewModel.delete(menu)
                finish()

            }


            editTextServingsNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val resultA = ((((fat * 9) + (carbs * 4) + (protein * 4))))
                    txtTotalCalCalculated.text = viewModel.getTotalCal(resultA.toString(), s.toString())
                    calculatedCalCarbs.text = ((viewModel.getCalCarbs(carbs)).toDouble()*((s.toString()).toDoubleOrNull() ?: 0.0)).toString()
                    calculatedCalFat.text = ((viewModel.getCalFat(fat)).toDouble()*((s.toString()).toDoubleOrNull() ?: 0.0)).toString()
                    calculatedCalProtein.text = ((viewModel.getCalProtein(protein)).toDouble()*((s.toString()).toDoubleOrNull() ?: 0.0)).toString()
                }
                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }
}