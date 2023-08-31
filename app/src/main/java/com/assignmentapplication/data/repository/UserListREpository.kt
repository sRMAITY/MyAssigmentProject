package com.assignmentapplication.data.repository

import androidx.lifecycle.MutableLiveData
import com.assignmentapplication.data.model.UserModel
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Singleton

@Singleton
interface UserListREpository {
    fun getUserList(userlist: MutableLiveData<List<UserModel>>)
}