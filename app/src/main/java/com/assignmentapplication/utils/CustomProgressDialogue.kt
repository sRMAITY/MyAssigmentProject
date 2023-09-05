package com.assignmentapplication.utils


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.assignmentapplication.R


class CustomProgressDialogue(  ) {



    private var dialog: Dialog? = null
    var context : Context ? =null

    companion object {

        private lateinit var instance: CustomProgressDialogue

        fun getInstance(): CustomProgressDialogue {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = CustomProgressDialogue()
                }
                return instance
            }

        }
    }

    /**
     * show custom progress dialog
     */
    fun showDialog(context: Context){
         if (dialog != null && dialog?.isShowing()!!) {
             return;
         }
         dialog = Dialog(context)
         dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);

         dialog?.setCancelable(false)
         dialog?.setCancelable(false)
         val view: View = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog, null)
         dialog?.setContentView(view)
         dialog?.show()
     }

    // show alert dialog
     fun showAlertdialog(context: Context, msg : String){
         val dialogBuilder = AlertDialog.Builder(context)
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

    /**
     * Dismiss progress dialog
     */
    fun dismissDialog(){
        if (dialog != null && dialog!!.isShowing()) {
            dialog?.dismiss();
        }
    }
}