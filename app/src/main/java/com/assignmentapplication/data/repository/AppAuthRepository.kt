package com.assignmentapplication.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

class  AppAuthRepository @Inject constructor(@ApplicationContext private val context: Context){
    private val firebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private lateinit var userLiveData: MutableLiveData<FirebaseUser>


    fun signup(email: String, password: String) {
         try {
             firebaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener {
                 if (it.isSuccessful){
                     userLiveData?.postValue(firebaseAuth.getCurrentUser());
                 }
             }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }


}