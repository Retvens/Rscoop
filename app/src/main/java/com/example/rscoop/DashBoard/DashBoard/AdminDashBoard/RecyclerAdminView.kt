package com.example.rscoop.DashBoard.DashBoard.AdminDashBoard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rscoop.DataCollections.CountryData
import com.example.rscoop.DataCollections.HotelsData
import com.example.rscoop.R

class RecyclerAdminView(val context: Context, var userList:List<CountryData>):RecyclerView.Adapter<RecyclerAdminView.MyViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
            mListener = listener
    }

    class MyViewHolder(itemview:View,listener:onItemClickListener):RecyclerView.ViewHolder(itemview){

        var name = itemview.findViewById<TextView>(R.id.countryName)
        var image = itemview.findViewById<ImageView>(R.id.CountryImage)

        init {

            itemview.setOnClickListener {
                listener.onClick(adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.countoryrows,parent,false)
        return MyViewHolder(view,mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.name.text = userList[position].Name
        Glide.with(context).load(userList[position].Country_photo).into(holder.image)


    }

    override fun getItemCount(): Int {
        return userList.size
    }
    fun updateData(newItems: List<CountryData>) {
        userList = newItems
        notifyDataSetChanged()
    }


}