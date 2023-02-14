package com.retvens.rscoop.RecentProperties

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retvence.rscoop.DataCollections.OwnersData
import com.retvens.rscoop.ClientInformation.ClientInfo
import com.retvens.rscoop.R


class ClientCountriesAdapter(val context: Context, var userList:List<OwnersData>):
    RecyclerView.Adapter<ClientCountriesAdapter.MyViewHolderClass>(){


    class MyViewHolderClass(itemview: View):RecyclerView.ViewHolder(itemview){

        var Name = itemview.findViewById<TextView>(R.id.clientName)
        var image = itemview.findViewById<ImageView>(R.id.clientImage)
        var whats = itemview.findViewById<ImageView>(R.id.whatsappCountry)
        val number = itemview.findViewById<TextView>(R.id.NumberOfClient)
        val address = itemview.findViewById<TextView>(R.id.addOfClient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.clientcountryxml,parent,false)
        return MyViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: MyViewHolderClass, position: Int) {

        val data = userList[position]

        holder.Name.text = userList[position].Name
        Glide.with(context).load(userList[position].Profile_photo).into(holder.image)
        holder.number.text = data.Phone.toString()
        holder.address.text = data.Country

        holder.whats.setOnClickListener {
            val uriWhats : Uri = Uri.parse("https://whatsapp.com")
            context.startActivity(Intent(Intent.ACTION_VIEW,uriWhats))
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ClientInfo::class.java)
            intent.putExtra("client_name",data.Name)
            intent.putExtra("client_image",data.Profile_photo)
            intent.putExtra("client_phone",data.Phone)
            intent.putExtra("client_address",data.Country)
            intent.putExtra("owner",data.owner_id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateData(newItems: List<OwnersData>) {
        userList = newItems
        notifyDataSetChanged()
    }
}