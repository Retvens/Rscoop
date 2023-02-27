package com.retvens.rscoop.RecentProperties

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.retvence.rscoop.DashBoardIgniter.RecentPropertiesDataClass
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvence.rscoop.DataCollections.HotelsLocation
import com.retvence.rscoop.DataCollections.TaskData
import com.retvens.rscoop.R

class RecentPropertiesAdapter(val context: Context, var propertyList: List<RecentPropertiesDataClass>) : RecyclerView.Adapter<RecentPropertiesAdapter.RecentPropertiesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentPropertiesViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_recent_properties,parent,false)
        return RecentPropertiesViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecentPropertiesViewHolder, position: Int) {
        val propertyList: RecentPropertiesDataClass = propertyList[position]
        holder.hotelName.text = propertyList.hotel_name
        holder.googleReview.text = propertyList.Google_review.toString()
        holder.tripadReview.text = propertyList.trip_advisor_review.toString()
        holder.ratingBar.rating = propertyList.hotel_stars.toFloat()
        holder.country.text = propertyList.Country

        Glide.with(context).load(propertyList.hotel_logo).into(holder.hotelCover)
        Glide.with(context).load(propertyList.hotel_logo).into(holder.hotel_profile)

        holder.itemView.setOnClickListener {
            val intent = Intent(context,HotelsLocations::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            intent.putExtra("Name",propertyList.hotel_name)
            intent.putExtra("image",propertyList.Cover_photo)
            intent.putExtra("logo",propertyList.hotel_logo)
            intent.putExtra("address",propertyList.Address)
            intent.putExtra("about",propertyList.About)
            intent.putExtra("star",propertyList.hotel_stars)
            intent.putExtra("google",propertyList.Google_review)
            intent.putExtra("trip",propertyList.trip_advisor_review)

            for (location in propertyList.hotel_location){
                intent.putExtra("latitude", location.Latitude)
                intent.putExtra("longitude",location.Longitude)
            }

            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return propertyList.size
    }
    fun updateProData(newProItems: List<RecentPropertiesDataClass>) {
        propertyList = newProItems
        notifyDataSetChanged()
    }

    class RecentPropertiesViewHolder(itemView: View) : ViewHolder(itemView){
        var hotelName = itemView.findViewById<TextView>(R.id.hotel_name)
        var hotelCover = itemView.findViewById<ImageView>(R.id.hotel_cover)
        var tripadReview = itemView.findViewById<TextView>(R.id.greview_review_text)
        var googleReview = itemView.findViewById<TextView>(R.id.google_review_text)
        var ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
        var country = itemView.findViewById<TextView>(R.id.hotel_location)
        var hotel_profile = itemView.findViewById<ImageView>(R.id.hotel_profile)
    }
}