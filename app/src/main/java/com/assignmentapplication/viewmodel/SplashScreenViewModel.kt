package com.assignmentapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignmentapplication.utils.AppSheardPreference
import com.assignmentapplication.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(val appSheardPreference: AppSheardPreference): ViewModel() {

    private var _islogCheck= MutableLiveData<Boolean>(false)
    val islogCheck : LiveData<Boolean> = _islogCheck

    fun initSplashScreen() {
        // add delay and login status check
        viewModelScope.launch {
            delay(2000)
            userIslogin()
        }
    }

     //check login data and update observer
    fun userIslogin(){
        if (!appSheardPreference.getData(Constants.islogIn).equals(""))
            _islogCheck.value = true
        else
            _islogCheck.value = false
    }

}