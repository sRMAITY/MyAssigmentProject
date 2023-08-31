package com.assignmentapplication.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assignmentapplication.R
import com.assignmentapplication.data.model.UserModel
import com.assignmentapplication.view.activity.MainActivity


class UserListAdapter(val context : MainActivity, val userList : ArrayList<UserModel>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView){
          val uName : TextView = itemView.findViewById(R.id.tv_user)
          val uEmail : TextView = itemView.findViewById(R.id.tv_useremail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val view : View = LayoutInflater.from(context).inflate(R.layout.item_user_list,parent,false)
         return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  userList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.uName.text = userList.get(position).username
        holder.uEmail.text = userList.get(position).userEmail

    }
}