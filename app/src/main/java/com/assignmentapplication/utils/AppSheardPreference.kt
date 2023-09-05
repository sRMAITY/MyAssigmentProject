package com.assignmentapplication.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSheardPreference @Inject constructor(@ApplicationContext private val context: Context){
    private lateinit var sharedPreferences: SharedPreferences

       fun getInstance(context: Context): SharedPreferences {
                if (!::sharedPreferences.isInitialized) {
                    sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                }
        return sharedPreferences
      }

     // get data from preference using key
        fun getData(key :String): String
        {

          return  getInstance(context).getString(key, "")!!
        }

      // save data in App Preference with key and value
        fun saveData(key : String,value: String) {
            getInstance(context).edit()
                .putString(key, value)
                .apply()
        }

     // clear preference
        fun clearAll() {
            getInstance(context).edit().clear().apply()
        }

}