package com.assignmentapplication.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignmentapplication.R
import com.assignmentapplication.data.model.UserModel
import com.assignmentapplication.databinding.ActivityMainBinding
import com.assignmentapplication.utils.Constants
import com.assignmentapplication.utils.CustomProgressDialogue
import com.assignmentapplication.utils.Resource
import com.assignmentapplication.view.adapters.UserListAdapter
import com.assignmentapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // binding for Main activity
    lateinit var activityMainBinding : ActivityMainBinding
    // user list adapter
    lateinit var userListAdapter : UserListAdapter
    // user array list
    var userlist : ArrayList<UserModel> = ArrayList()
    // Inject main viewmodel
    val mainViewModel by viewModels<MainViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        // logout button click
        activityMainBinding.imgLogout.setOnClickListener {
            mainViewModel.logout()
        }
        // set layout manager in recyclerview
        activityMainBinding.recUserList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        CustomProgressDialogue.getInstance().showDialog(this)

        //show user name
        activityMainBinding.tvUsername.text = resources.getString(R.string.hello)+ mainViewModel.appSheardPreference.getData(Constants.userName)

        // get user list
        mainViewModel.getUserList()

         // user list adapter
          mainViewModel.userlist.observe(this ){
               userlist = it as ArrayList<UserModel>
              userListAdapter = UserListAdapter(this, userlist )
              activityMainBinding.recUserList.adapter = userListAdapter
              CustomProgressDialogue.getInstance().dismissDialog()
          }

        // logout data observer
        lifecycleScope.launch {
            mainViewModel?.loginUser?.collect{ value ->
                when(value){
                    // failure
                    is Resource.Failure -> {
                      //  Toast.makeText(this@MainActivity,value.exception.toString(), Toast.LENGTH_LONG ).show()
                        CustomProgressDialogue.getInstance().dismissDialog()
                        CustomProgressDialogue.getInstance().showAlertdialog(this@MainActivity,value.exception.toString())
                    }
                    // Success
                    is Resource.Success ->{
                        Toast.makeText(this@MainActivity,resources.getString(R.string.logout), Toast.LENGTH_LONG ).show()
                        CustomProgressDialogue.getInstance().dismissDialog()
                        mainViewModel.appSheardPreference.clearAll()
                        val i = Intent(this@MainActivity,LoginActivity::class.java)
                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(i)

                    }
                    // Loading
                    is Resource.Loading ->{
                        CustomProgressDialogue.getInstance().showDialog(this@MainActivity)
                    }
                    // Logout
                    is Resource.LogOutcase ->{
                        Toast.makeText(this@MainActivity,resources.getString(R.string.logout), Toast.LENGTH_LONG ).show()
                        CustomProgressDialogue.getInstance().dismissDialog()
                        startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                        finish()
                    }

                    else -> {
                    }
                }
            }
        }
    }
}