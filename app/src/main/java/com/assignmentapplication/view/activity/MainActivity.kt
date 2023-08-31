package com.assignmentapplication.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.assignmentapplication.R
import com.assignmentapplication.data.model.UserModel
import com.assignmentapplication.databinding.ActivityLoginBinding
import com.assignmentapplication.databinding.ActivityMainBinding
import com.assignmentapplication.utils.CustomProgressDialogue
import com.assignmentapplication.utils.Resource
import com.assignmentapplication.view.adapters.UserListAdapter
import com.assignmentapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding : ActivityMainBinding
    lateinit var userListAdapter : UserListAdapter
    var userlist : ArrayList<UserModel> = ArrayList()
    val mainViewModel by viewModels<MainViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.imgLogout.setOnClickListener {
            mainViewModel.logout()
        }

        activityMainBinding.recUserList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        CustomProgressDialogue.getInstance().showDialog(this)
        mainViewModel.getUserList()


          mainViewModel.userlist.observe(this ){
               userlist = it as ArrayList<UserModel>
              userListAdapter = UserListAdapter(this, userlist )
              activityMainBinding.recUserList.adapter = userListAdapter
              CustomProgressDialogue.getInstance().dismissDialog()
          }

        lifecycleScope.launch {
            mainViewModel?.loginUser?.collect{ value ->
                when(value){
                    is Resource.Failure -> {
                        Toast.makeText(this@MainActivity,value.exception.toString(), Toast.LENGTH_LONG ).show()
                        CustomProgressDialogue.getInstance().dismissDialog()
                    }
                    is Resource.Success ->{
                        Toast.makeText(this@MainActivity," Logout successfully", Toast.LENGTH_LONG ).show()
                        CustomProgressDialogue.getInstance().dismissDialog()
                        startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                        finish()
                    }
                    is Resource.Loading ->{
                        CustomProgressDialogue.getInstance().showDialog(this@MainActivity)
                    }
                    is Resource.LogOutcase ->{
                        Toast.makeText(this@MainActivity," Logout successfully", Toast.LENGTH_LONG ).show()
                        CustomProgressDialogue.getInstance().dismissDialog()
                        startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                        finish()
                    }

                    else -> {
                        // Toast.makeText(this@LoginActivity,"Something wrong, Try again later", Toast.LENGTH_LONG ).show()

                    }
                }
            }
        }
    }
}