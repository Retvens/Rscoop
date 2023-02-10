package com.example.rscoop.ClientInformation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rscoop.DataCollections.HotelsData
import com.example.rscoop.R
import com.example.rscoop.RecentProperties.HotelsLocations

class ClientAdapter(val context: Context, var userList:List<HotelsData>):RecyclerView.Adapter<ClientAdapter.ViewHolder>() {


    class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        var name = itemview.findViewById<TextView>(R.id.bookmark_Name)
        var image = itemview.findViewById<ImageView>(R.id.bookmark_Image)
        var rating = itemview.findViewById<TextView>(R.id.bookmark_Rating)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.bookmarkscreen,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = userList[position]

        holder.name.text = userList[position].hotel_name
        Glide.with(context).load(userList[position].hotel_profile_photo).into(holder.image)
        holder.rating.text = userList[position].hotel_stars.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, HotelsLocations::class.java)
            intent.putExtra("item_name", item.hotel_name)
            intent.putExtra("item_image", item.hotel_profile_photo)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  userList.size
    }
    private fun itemClicked(item: HotelsData) {
        val intent = Intent(context, HotelsLocations::class.java)
        intent.putExtra("item_name", item.hotel_name)
        intent.putExtra("item_image", item.hotel_profile_photo)
        context.startActivity(intent)
    }
}