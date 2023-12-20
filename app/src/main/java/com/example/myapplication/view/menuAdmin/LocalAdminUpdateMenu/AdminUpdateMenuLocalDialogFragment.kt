package com.example.myapplication.view.menuAdmin.LocalAdminUpdateMenu

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.model.room.MenuAdmin
import com.example.myapplication.databinding.DialogfragmentAdminupdatemenuBinding
import com.example.myapplication.util.CalorieCalculator



//MENU ADMIN YAAP

class AdminUpdateMenuLocalDialogFragment : DialogFragment() {

    private lateinit var binding : DialogfragmentAdminupdatemenuBinding
    private lateinit var viewModel: AdminUpdateLocalViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogfragmentAdminupdatemenuBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        viewModel = ViewModelProvider(this)[AdminUpdateLocalViewModel::class.java]
        viewModel.initializeDBRoom(requireContext())


        setUpTextWatchers()
        val menu = arguments?.getSerializable("menu object") as MenuAdmin
        with(binding) {

            val nama = menu.name
            val cal100 = menu.calAmount
            val carbs = menu.carbsGram
            val protein = menu.proteinGram
            val fat = menu.fatGram

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
                viewModel.delete(menu)
                dismiss()
            }

            btnUpdate.setOnClickListener {
                val a = MenuAdmin(
                    id = menu.id,
                    name = txtName.text.toString(),
                    fatGram = inputFat.text.toString(),
                    carbsGram = inputCarbs.text.toString(),
                    proteinGram = inputProtein.text.toString(),
                    calAmount = CalorieCalculator.getTotalCal100(
                        inputCarbs.text.toString(),
                        inputProtein.text.toString(),
                        inputFat.text.toString()
                    )
                )
                viewModel.update(a)
                dismiss()



            }
        }
        return dialog
    }


    private fun setUpTextWatchers() {
        with(binding) {
            val watchers = arrayOf(
                Pair(inputCarbs, calculatedCalCarbs),
                Pair(inputProtein, calculatedCalProtein),
                Pair(inputFat, calculatedCalFat)
            )
            watchers.forEach { (input, calculatedCal) ->
                input.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val calResult = when (calculatedCal) {
                            calculatedCalCarbs -> CalorieCalculator.getCalCarbs(s.toString()).toString()
                            calculatedCalProtein -> CalorieCalculator.getCalProtein(s.toString()).toString()
                            calculatedCalFat -> CalorieCalculator.getCalFat(s.toString()).toString()
                            else -> "0"
                        }

                        calculatedCal.text = "$calResult cal"
                        updateAllCalories()
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })
            }
        }
    }

    private fun updateAllCalories() {
        with(binding) {
            val result = CalorieCalculator.getCalculatedAllCalories(
                inputCarbs.text.toString(),
                inputProtein.text.toString(),
                inputFat.text.toString()
            )
            calculatedAllCal1serving.text = "$result calories in 100 gr"
        }
    }

}
