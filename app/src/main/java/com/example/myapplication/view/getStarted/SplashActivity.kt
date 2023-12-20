package com.example.myapplication.view.getStarted

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.view.menuAdmin.HomepageAdminActivity
import com.example.myapplication.view.menuUser.HomepageActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel : SplashActivityViewModel
    private lateinit var intent1 : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        viewModel = ViewModelProvider(this)[SplashActivityViewModel::class.java]

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        if(viewModel.checkLoginStatus()){
            if(viewModel.getRole() == "admin"){
                intent1 = Intent(this, HomepageAdminActivity::class.java)
            } else if (viewModel.getRole() == "user"){
                intent1 = Intent(this, HomepageActivity::class.java)
            }
        } else {
            intent1 = Intent(this, WellcomingActivity::class.java)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent1)
            finish()
        }, 3000)

    }
}