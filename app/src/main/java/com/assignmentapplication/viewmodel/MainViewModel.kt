package com.assignmentapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignmentapplication.data.model.UserModel
import com.assignmentapplication.data.repository.AuthRepository
import com.assignmentapplication.data.repository.UserListREpository
import com.assignmentapplication.utils.AppSheardPreference
import com.assignmentapplication.utils.NetworkHelper
import com.assignmentapplication.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val repository: AuthRepository,
                                        private val userListREpository: UserListREpository,
                                        private val networkHelper: NetworkHelper,
                                         val appSheardPreference: AppSheardPreference) : ViewModel(){

   //  mutable data for user list
    private val _userlist  = MutableLiveData<List<UserModel>>()
    val userlist : LiveData<List<UserModel>> =_userlist


    // mutable data for login user
    private val _logginuser = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginUser: StateFlow<Resource<FirebaseUser>?> = _logginuser

    /**
     * logout user
     */
    fun logout(){
        if (networkHelper.isNetworkConnected()) {
            var result = repository.logout()
            _logginuser?.value = result
        }

    }

    /**
     * get user list from firebase user list
     */
    fun getUserList(){
       if (networkHelper.isNetworkConnected())
           userListREpository.getUserList(_userlist)
    }
}