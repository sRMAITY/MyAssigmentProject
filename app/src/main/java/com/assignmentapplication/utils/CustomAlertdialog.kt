package com.assignmentapplication.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.assignmentapplication.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomAlertdialog @Inject constructor(@ApplicationContext private val context: Context){

    fun showDialog(msg : String){
        val dialogBuilder = AlertDialog.Builder(context as Activity, R.style.AppFullScreenTheme)
        // set message for alert dialog box
        dialogBuilder.setMessage(msg)
            .setCancelable(false)

            .setPositiveButton("Ok", DialogInterface.OnClickListener {
                    dialog, id -> dialog.dismiss()
            })
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle(context.resources.getString(R.string.app_name))
        // show alert dialog
        alert.show()
    }

}