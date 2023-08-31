package com.assignmentapplication.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.assignmentapplication.databinding.ActivityLoginBinding


import com.assignmentapplication.utils.CustomProgressDialogue
import com.assignmentapplication.utils.Resource
import com.assignmentapplication.viewmodel.LogInViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import javax.annotation.meta.When

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel by viewModels<LogInViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.login.setOnClickListener {
           if(checkvalidation()) {
               authViewModel.loginUser(
                   binding.username.text.toString(),
                   binding.password.text.toString())
           } else
               Toast.makeText(this, "Please enter credential", Toast.LENGTH_LONG).show()
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
            finish()
        }

      lifecycleScope.launch {
      authViewModel?.loginFlow?.collect{ value ->
          when(value){
              is Resource.Failure -> {

                  Toast.makeText(this@LoginActivity,value.exception.toString(), Toast.LENGTH_LONG ).show()

                  CustomProgressDialogue.getInstance().dismissDialog()
              }
              is Resource.Success ->{
                  Toast.makeText(this@LoginActivity,"sucess", Toast.LENGTH_LONG ).show()
                  CustomProgressDialogue.getInstance().dismissDialog()
                  startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                  finish()
              }
              is Resource.Loading ->{
                  CustomProgressDialogue.getInstance().showDialog(this@LoginActivity)
              }

              else -> {
                 // Toast.makeText(this@LoginActivity,"Something wrong, Try again later", Toast.LENGTH_LONG ).show()

              }
          }
       }
      }


    }

    private fun checkvalidation(): Boolean {
        if ( binding.username.text.toString().equals(""))
            return false
        if ( binding.password.text.toString().equals(""))
            return false
        return true


    }
}