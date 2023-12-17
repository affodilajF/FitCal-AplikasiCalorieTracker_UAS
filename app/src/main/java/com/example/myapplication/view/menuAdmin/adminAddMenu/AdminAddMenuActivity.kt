package com.example.myapplication.view.menuAdmin.adminAddMenu

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.model.room.MenuAdmin
import com.example.myapplication.databinding.ActivityAdminAddMenuBinding
import com.example.myapplication.util.CalorieCalculator
import com.example.myapplication.util.Network


class AdminAddMenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminAddMenuBinding

    private lateinit var viewModel: AdminAddMenuViewModel

    private var temporaryCarbs = "0.0"
    private var temporaryProtein= "0.0"
    private var temporaryFat = "0.0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityAdminAddMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AdminAddMenuViewModel::class.java]
        viewModel.initializeDBRoom(this)
//        viewModel.getData()
//        viewModel.syncToFirestore(this)

//        val connectivityLiveData = Network.startCheckingInternetConnectivity(this)
////
//        connectivityLiveData.observe(this) { isConnected ->
//            if(isConnected){
////                masukkin ke firestore
////                viewModel.syncToFirestore(this)
////                viewModel.syncToFirestore(){
////                    result->
////                    if(result){
////                        viewModel.deleteAllUnsyncedDataFromRoom()
////
////                    }
////                }
//
//            }else{
//
//            }
//        }


//        viewModel.loginUser(email, password) { result ->
//            if (result) {
//                viewModel.fetchUserRoleFromFirestore()
//            }
//        }}













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








        }

        with(binding){
            btnDone.setOnClickListener {
                viewModel.initializeDBRoom(this@AdminAddMenuActivity)

                val name = txtName.text.toString()

                // carbsGram
                val c : String =inputCarbs.text.toString().takeIf { it.isNotBlank() } ?: "0"
//                fatGram
                val d: String = inputFat.text.toString().takeIf { it.isNotBlank() } ?: "0"
//                proteinGram
                val e : String = inputProtein.text.toString().takeIf { it.isNotBlank() } ?: "0"

                val totalcal100 = CalorieCalculator.getTotalCal100(c.toIntOrNull() ?: 0 , d.toIntOrNull() ?: 0 , e.toIntOrNull() ?: 0)
                val menuToadd = MenuAdmin(name=name, calAmount = totalcal100.toString(), carbsGram = c, fatGram = d, proteinGram = e)
                viewModel.addMenu(menuToadd, this@AdminAddMenuActivity)
                finish()
            }

        }








    }
}