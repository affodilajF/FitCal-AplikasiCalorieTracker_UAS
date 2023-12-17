package com.example.myapplication.view.menuAdmin.adminUpdateMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.data.model.firestore.Menu
import com.example.myapplication.databinding.ActivityAdminUpdateMenuBinding
import com.example.myapplication.util.CalorieCalculator
import com.example.myapplication.view.menuAdmin.adminAddMenu.AdminAddMenuViewModel

class AdminUpdateMenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminUpdateMenuBinding

    private var temporaryCarbs = "0.0"
    private var temporaryProtein= "0.0"
    private var temporaryFat = "0.0"

    private lateinit var viewModel: AdminUpdateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_admin_update_menu)
        binding = ActivityAdminUpdateMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AdminUpdateViewModel::class.java]
        viewModel.initializeDBRoom(this)

        binding.btnBack.setOnClickListener {
            finish()
        }


        with(binding){
            val menu = intent.getSerializableExtra("menu object") as Menu
            val nama = menu.name
            val cal100 = menu.calAmount
            val carbs = menu.carbsGram
            val protein = menu.proteinGram
            val fat = menu.fatGram

            temporaryCarbs = carbs
            temporaryFat = fat
            temporaryProtein = protein

            val editableName = Editable.Factory.getInstance().newEditable(nama)
            txtName.text = editableName

            calculatedAllCal1serving.text = cal100 + " calories in 100 gr"

            val editableGrCarbs = Editable.Factory.getInstance().newEditable(carbs)
            inputCarbs.text = editableGrCarbs
            calculatedCalCarbs.text = CalorieCalculator.getCalCarbs(carbs).toString() + " cal"

            val editableGrProtein = Editable.Factory.getInstance().newEditable(protein)
            inputProtein.text = editableGrProtein
            calculatedCalProtein.text = CalorieCalculator.getCalProtein(protein).toString() + " cal"

            val editableGrFat = Editable.Factory.getInstance().newEditable(fat)
            inputFat.text = editableGrFat
            calculatedCalFat.text = CalorieCalculator.getCalFat(fat).toString() + " cal"




            btnDel.setOnClickListener {
                viewModel.deleteMenu(menu)
                finish()
            }
            btnUpdate.setOnClickListener {

                val a = Menu(id = menu.id, name=txtName.text.toString(),
                    fatGram = inputFat.text.toString(), carbsGram = inputCarbs.text.toString(), proteinGram = inputProtein.text.toString(),
                    calAmount = CalorieCalculator.getTotalCal100(inputCarbs.text.toString(), inputProtein.text.toString(), inputFat.text.toString())
                )
                viewModel.updateMenu(a)
                finish()
            }


        }





        with(binding){
            inputCarbs.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    calculatedCalCarbs.text = CalorieCalculator.getCalCarbs(s.toString()).toString() + " cal"
                    temporaryCarbs = s.toString()

                    calculatedAllCal1serving.text = CalorieCalculator.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat) + " calories in 100 gr"

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
                    calculatedAllCal1serving.text = CalorieCalculator.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat) + " calories in 100 gr"


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
                    calculatedAllCal1serving.text = CalorieCalculator.getCalculatedAllCalories(temporaryCarbs, temporaryProtein, temporaryFat) + " calories in 100 gr"
                }
                override fun afterTextChanged(s: Editable?) {
                }
            })






        }}
}