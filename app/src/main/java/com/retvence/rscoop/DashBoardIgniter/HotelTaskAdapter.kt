package com.retvence.rscoop.DashBoardIgniter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retvence.rscoop.DataCollections.TaskData
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.retvens.rscoop.R

class HotelTaskAdapter(val context: Context, var userList:List<TaskData>):RecyclerView.Adapter<HotelTaskAdapter.viewHolder>() {


    class viewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        var name = itemview.findViewById<TextView>(R.id.Company_Name)
        var image = itemview.findViewById<ImageView>(R.id.owner_Image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.egnitertaskcard,parent,false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
            holder.name.text = userList[position].hotel_name
            Glide.with(context).load(userList[position].owner_pic).into(holder.image)
    }

    override fun getItemCount(): Int {
            return userList.size
    }


}