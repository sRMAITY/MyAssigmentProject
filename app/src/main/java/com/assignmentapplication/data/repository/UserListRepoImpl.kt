package com.assignmentapplication.data.repository

import androidx.lifecycle.MutableLiveData
import com.assignmentapplication.data.model.UserModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import javax.inject.Inject

class UserListRepoImpl @Inject constructor(private val databaseReference: FirebaseDatabase) : UserListREpository{

    override fun getUserList(userlist: MutableLiveData<List<UserModel>>) {
        val databaseref : DatabaseReference =  databaseReference.getReference("users")
        databaseref.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                  try {
                      val _userlist: List<UserModel> = snapshot.children.map {dataSnapshot ->
                          dataSnapshot.getValue(UserModel::class.java)!!
                      }
                      userlist.postValue(_userlist)
                  }catch (e: Exception)
                  {
                      e.printStackTrace()
                  }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}