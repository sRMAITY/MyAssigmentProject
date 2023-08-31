package com.assignmentapplication.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.assignmentapplication.R
import com.assignmentapplication.data.model.UserModel
import com.assignmentapplication.databinding.ActivityRegistrationBinding
import com.assignmentapplication.utils.CustomProgressDialogue
import com.assignmentapplication.utils.Resource
import com.assignmentapplication.viewmodel.RegistrationViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private val regviewmodel by viewModels<RegistrationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signup.setOnClickListener {
            if (checkvalidation())
            regviewmodel.signupUser(binding.username.text.toString(),binding.useremail.text.toString(),
                binding.password.text.toString())
            else
                Toast.makeText(this, "Please fill up all details", Toast.LENGTH_LONG).show()
        }
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    lifecycleScope.launch {
        regviewmodel?.signupFlow?.collect() {
            when (it) {
                is Resource.Failure -> {
                    CustomProgressDialogue.getInstance().dismissDialog()
                    Toast.makeText(this@RegistrationActivity, it.exception.toString(), Toast.LENGTH_LONG).show()
                }

                is Resource.Success -> {

                     val userData = UserModel(binding.username.text.toString(), binding.useremail.text.toString())
                     Firebase.database.getReference("users").push().setValue(userData)
                     Toast.makeText(this@RegistrationActivity,it.result.displayName + "Register Successfully" , Toast.LENGTH_LONG).show()
                      CustomProgressDialogue.getInstance().dismissDialog()
                     startActivity(Intent(this@RegistrationActivity,LoginActivity::class.java))
                      finish()
                }

                is Resource.Loading -> {
                    CustomProgressDialogue.getInstance().showDialog(this@RegistrationActivity)
                }

                else -> {

                }
            }
        }
    }


    }


    private fun checkvalidation(): Boolean {
        if ( binding.useremail.text.toString().equals(""))
            return false
        if ( binding.username.text.toString().equals(""))
            return false
        if ( binding.password.text.toString().equals(""))
            return false
        return true

    }


}