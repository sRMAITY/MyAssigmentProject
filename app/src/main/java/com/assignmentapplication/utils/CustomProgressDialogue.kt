package com.assignmentapplication.utils


import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
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

    fun dismissDialog(){
        if (dialog != null && dialog!!.isShowing()) {
            dialog?.dismiss();
        }
    }
}