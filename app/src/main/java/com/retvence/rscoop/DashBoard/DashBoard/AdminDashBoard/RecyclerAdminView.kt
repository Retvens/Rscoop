package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retvence.rscoop.DataCollections.CountryData
import com.retvens.rscoop.R
import com.retvens.rscoop.RecentProperties.ClientCountries

class RecyclerAdminView(val context: Context, var userList:List<CountryData>):RecyclerView.Adapter<RecyclerAdminView.MyViewHolder>(){

    class MyViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){

        var name = itemview.findViewById<TextView>(R.id.countryName)
        var image = itemview.findViewById<ImageView>(R.id.CountryImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.countoryrows,parent,false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = userList[position]

        holder.name.text = data.Name
        Glide.with(context).load(data.Country_photo).into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ClientCountries::class.java)
            intent.putExtra("country",data.Name)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<CountryData>) {
        userList = newItems
        notifyDataSetChanged()
    }


}