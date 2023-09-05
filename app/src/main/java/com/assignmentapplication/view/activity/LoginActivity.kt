package com.assignmentapplication.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.lifecycleScope
import com.assignmentapplication.R
import com.assignmentapplication.databinding.ActivityLoginBinding
import com.assignmentapplication.utils.Constants


import com.assignmentapplication.utils.CustomProgressDialogue
import com.assignmentapplication.utils.Resource
import com.assignmentapplication.viewmodel.LogInViewModel

import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    // binding for login activity
    private lateinit var binding: ActivityLoginBinding

    // Log in View Model Inject
    private val authViewModel by viewModels<LogInViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

     // login button click
        binding.login.setOnClickListener {
           if(checkvalidation()) {
               // call login
               authViewModel.loginUser(
                   binding.username.text.toString(),
                   binding.password.text.toString())
           } else
               CustomProgressDialogue.getInstance().showAlertdialog(this,resources.getString(R.string.entercredential))
        }

        //registration button click
        binding.tvSignup.setOnClickListener {
            //go to Sign up activity
            startActivity(Intent(this,RegistrationActivity::class.java))
            finish()
        }

        /**`
         * Login user data observer
          */
      lifecycleScope.launch {
      authViewModel?.loginFlow?.collect{ value ->
          when(value){
              //LogIn Failure
              is Resource.Failure -> {
                 // Toast.makeText(this@LoginActivity,value.exception.toString(), Toast.LENGTH_LONG ).show()
                  CustomProgressDialogue.getInstance().dismissDialog()
                  CustomProgressDialogue.getInstance().showAlertdialog(this@LoginActivity,value.exception.toString())
              }
              // LogIn Success
              is Resource.Success ->{
                  Toast.makeText(this@LoginActivity,"Successfully loggedIn", Toast.LENGTH_LONG ).show()
                  CustomProgressDialogue.getInstance().dismissDialog()
                   authViewModel.appSheardPreference.saveData(Constants.islogIn,"1")
                   authViewModel.appSheardPreference.saveData(Constants.userName, value.result.displayName!!)
                   val i = Intent(this@LoginActivity,MainActivity::class.java)
                   startActivity(i)
                   finish()
              }
              // Show Loader
              is Resource.Loading ->{
                  CustomProgressDialogue.getInstance().showDialog(this@LoginActivity)
              }

              else -> {
              }
          }
       }
      }


    }

    /**
     * check username and password field blank
     */
    private fun checkvalidation(): Boolean {
        if ( binding.username.text.toString().equals(""))
            return false
        if ( binding.password.text.toString().equals(""))
            return false
        return true


    }
}