package com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retvence.rscoop.DataCollections.OwnersData
import com.retvens.rscoop.R
import com.retvens.rscoop.RecentProperties.ClientCountriesAdapter

class SelectAdapter(val context: Context, var userList:List<OwnersData>):
    RecyclerView.Adapter<SelectAdapter.MyViewHolderClass>(){

    class MyViewHolderClass(itemview: View):RecyclerView.ViewHolder(itemview) {
        var Name = itemview.findViewById<TextView>(R.id.clientName)
        var image = itemview.findViewById<ImageView>(R.id.clientImage)
        val number = itemview.findViewById<TextView>(R.id.NumberOfClient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.selectclientxml,parent,false)
        return MyViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: MyViewHolderClass, position: Int) {
        val data = userList[position]

        holder.Name.text = userList[position].Name
        Glide.with(context).load(userList[position].Profile_photo).into(holder.image)
        holder.number.text = data.Phone.toString()

        holder.itemView.setOnClickListener {
           val intent = Intent(context,AddProperty::class.java)
            intent.putExtra("id",data.owner_id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}