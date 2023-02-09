package com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerAdminView
import com.example.rscoop.DataCollections.OwnersData
import com.example.rscoop.R

class RecentRecycler(val context: Context, val userList:List<OwnersData>) : RecyclerView.Adapter<RecentRecycler.MyViewHolder>(){

    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){

        var name = itemview.findViewById<TextView>(R.id.Company_Name)
        var image = itemview.findViewById<ImageView>(R.id.owner_Image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recentcard,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = userList[position].Name
        Glide.with(context).load(userList[position].Profile_photo).into(holder.image)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}