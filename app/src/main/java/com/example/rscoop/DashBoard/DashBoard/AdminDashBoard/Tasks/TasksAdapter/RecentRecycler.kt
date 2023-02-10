package com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerAdminView
import com.example.rscoop.DataCollections.HotelsData
import com.example.rscoop.DataCollections.OwnersData
import com.example.rscoop.DataCollections.TaskData
import com.example.rscoop.R

class RecentRecycler(val context: Context, var userList:List<TaskData>) : RecyclerView.Adapter<RecentRecycler.MyViewHolder>(){

    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){

        var name = itemview.findViewById<TextView>(R.id.Company_Name)
        var image = itemview.findViewById<ImageView>(R.id.owner_Image)

        var facebook = itemview.findViewById<TextView>(R.id.facebooktask)
        var instagram = itemview.findViewById<TextView>(R.id.instatask)
        var linkdin = itemview.findViewById<TextView>(R.id.linkdin)
        var twitter = itemview.findViewById<TextView>(R.id.twitter)
        var pinterest = itemview.findViewById<TextView>(R.id.pinterest)
        var gmb = itemview.findViewById<TextView>(R.id.gg)
        var google = itemview.findViewById<TextView>(R.id.google)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recentcard,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.facebook.text = userList[position].facebook
        holder.instagram.text = userList[position].instagram
        holder.linkdin.text = userList[position].Linkedin
        holder.twitter.text = userList[position].twitter
        holder.pinterest.text = userList[position].Pinterest
        holder.gmb.text = userList[position].GMB
        holder.google.text = userList[position].Google_reviews
        Glide.with(context).load(userList[position].owner_pic).into(holder.image)
        holder.name.text = userList[position].hotel_name
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateData(newItems: List<TaskData>) {
        userList = newItems
        notifyDataSetChanged()
    }
}