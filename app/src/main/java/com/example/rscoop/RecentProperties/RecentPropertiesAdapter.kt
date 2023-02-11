package com.retvens.rscoop.RecentProperties

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.rscoop.DataCollections.HotelsData
import com.retvens.rscoop.R


class RecentPropertiesAdapter(val context: Context,val propertyList: List<HotelsData>) : RecyclerView.Adapter<RecentPropertiesAdapter.RecentPropertiesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentPropertiesViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_recent_properties,parent,false)
        return RecentPropertiesViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecentPropertiesViewHolder, position: Int) {
        val propertyList: HotelsData = propertyList[position]
        holder.hotelName.text = propertyList.hotel_name
        holder.ratingBar.rating = propertyList.hotel_stars.toFloat()
        Glide.with(context).load(propertyList.hotel_profile_photo).into(holder.hotelCover)
    }

    override fun getItemCount(): Int {
        return propertyList.size
    }

    class RecentPropertiesViewHolder(itemView: View) : ViewHolder(itemView){
        var hotelName = itemView.findViewById<TextView>(R.id.hotel_name)
        var hotelCover = itemView.findViewById<ImageView>(R.id.hotel_cover)
        var ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
    }
}