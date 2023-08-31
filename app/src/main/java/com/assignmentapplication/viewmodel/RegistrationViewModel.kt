package com.assignmentapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignmentapplication.data.repository.AuthRepository
import com.assignmentapplication.utils.MyCustomException
import com.assignmentapplication.utils.NetworkHelper
import com.assignmentapplication.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: AuthRepository,
                                                private val networkHelper: NetworkHelper,
                                              ) : ViewModel() {

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow


    fun signupUser(name: String, email: String, password: String) = viewModelScope.launch {
        if (networkHelper.isNetworkConnected()) {
             _signupFlow.value = Resource.Loading
              val result = repository.signup(name, email, password)
             _signupFlow.value = result

        }else
            _signupFlow.value = Resource.Failure(MyCustomException("No internet connection"))
    }


/*
    fun register(email: String?, password: String?) {
        appAuthRepository.signup(email!!, password!!)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }*/

}
