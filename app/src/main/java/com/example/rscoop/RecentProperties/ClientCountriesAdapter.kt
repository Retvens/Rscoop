package com.example.rscoop.RecentProperties

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerAdminView
import com.example.rscoop.DataCollections.CountryData
import com.example.rscoop.DataCollections.OwnersData
import com.example.rscoop.R

class ClientCountriesAdapter(val context: Context, val userList:List<OwnersData>):
    RecyclerView.Adapter<ClientCountriesAdapter.MyViewHolderClass>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class MyViewHolderClass(itemview: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemview){

        var Name = itemview.findViewById<TextView>(R.id.clientName)
        var image = itemview.findViewById<ImageView>(R.id.clientImage)

        init {

            itemview.setOnClickListener {
                listener.onClick(adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.clientcountryxml,parent,false)
        return MyViewHolderClass(view, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolderClass, position: Int) {
        holder.Name.text = userList[position].Name
        Glide.with(context).load(userList[position].Profile_photo).into(holder.image)

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}