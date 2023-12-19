package com.example.myapplication.view.menuUser.addMenu.updateMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import com.example.myapplication.R
import com.example.myapplication.data.model.room.MenuData
import com.example.myapplication.databinding.ActivityUpdateMenuBinding
import android.transition.TransitionManager
import android.view.animation.AlphaAnimation
import com.example.myapplication.util.CalorieCalculator


class UpdateMenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateMenuBinding
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

            val menu = intent.getSerializableExtra("MenuData object") as MenuData
            val name = menu.name
            val calories = menu.calAmount
            val carbs = menu.carbsGram
            val protein = menu.proteinGram
            val fat = menu.fatGram
            val serving = menu.servings


            txtName.text = name
            txtCalCarbs.text = CalorieCalculator.getCalCarbs(carbs) + " cal"
            txtCalFat.text = CalorieCalculator.getCalFat(fat) + " cal"
            txtCalProtein.text = CalorieCalculator.getCalProtein(protein) + " cal"

            txtGrCarbs.text = "$carbs gr"
            txtGrFat.text = "$fat gr"
            txtGrProtein.text = "$protein gr"

            val totalCal = CalorieCalculator.getTotalCal100(carbs,protein, fat)
            txtCalOneserving.text = "$totalCal cal"


            val servingString = serving.toString()
            val editableServings = Editable.Factory.getInstance().newEditable(servingString)
            editTextServingsNumber.text = editableServings

            txtTotalCalCalculated.text = "$calories cal"


            calculatedCalCarbs.text = CalorieCalculator.getCalCarbsOnUserServing(carbs.toString(), serving.toString())
            calculatedCalFat.text = CalorieCalculator.getCalFatOnUserServing(fat.toString(), serving.toString())
            calculatedCalProtein.text = CalorieCalculator.getCalProteinOnUserServing(protein.toString(), serving.toString())

            txtDate.text = menu.date
            txtCategory.text = menu.category


            btnUpdate.setOnClickListener {
                val newTotalCal = txtTotalCalCalculated.text.toString()
                val newTotalCal2 = newTotalCal.toIntOrNull() ?: 0
                val servBaru = editTextServingsNumber.text.toString()
                val servBaru2 = servBaru.toDoubleOrNull() ?: 0.0

                val menuFix = MenuData(id = menu.id, userId = menu.userId, name=menu.name, fatGram = menu.fatGram, carbsGram = menu.carbsGram
                , proteinGram = menu.proteinGram, date = menu.date, category = menu.category,
                    calAmount = newTotalCal2, servings = servBaru2, calAmount100 = menu.calAmount100)
                viewModel.update(menuFix)
                finish()
            }

            btnDel.setOnClickListener {
                viewModel.delete(menu)
                finish()

            }


            btnSeeMoreData1serving.setOnClickListener {
                if (layoutData1serving.visibility == View.VISIBLE) {
                    val fadeOut = AlphaAnimation(1f, 0f)
                    fadeOut.duration = 1
                    layoutData1serving.startAnimation(fadeOut)

                    layoutData1serving.visibility = View.GONE
                    btnSeeMoreData1serving.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                } else {
                    TransitionManager.beginDelayedTransition(layoutData1serving)
                    layoutData1serving.visibility = View.VISIBLE
                    btnSeeMoreData1serving.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                }
            }


            editTextServingsNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    txtTotalCalCalculated.text = CalorieCalculator.getTotalCal(CalorieCalculator.getTotalCal100(carbs,protein, fat).toString(), s.toString())

                    calculatedCalCarbs.text = ((CalorieCalculator.getCalCarbs(carbs)).toDouble()*((s.toString()).toDoubleOrNull() ?: 0.0)).toString()
                    calculatedCalFat.text = ((CalorieCalculator.getCalFat(fat)).toDouble()*((s.toString()).toDoubleOrNull() ?: 0.0)).toString()
                    calculatedCalProtein.text = ((CalorieCalculator.getCalProtein(protein)).toDouble()*((s.toString()).toDoubleOrNull() ?: 0.0)).toString()
                }
                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }
}