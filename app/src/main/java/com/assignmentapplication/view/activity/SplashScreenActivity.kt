package com.assignmentapplication.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.assignmentapplication.R
import com.assignmentapplication.viewmodel.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    //splash screen view Model
    private val splashViewModel by viewModels<SplashScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // init screen
        splashViewModel.initSplashScreen()

        // is login user observer
        splashViewModel.islogCheck.observe(this){
            if (it) {
                val i =Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }else {
               val i =Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }
}