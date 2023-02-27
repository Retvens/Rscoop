package com.retvence.rscoop.DashBoardIgniter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.retvence.rscoop.DataCollections.TaskData
import com.retvens.rscoop.R
import com.retvens.rscoop.RecentProperties.HotelsLocations

class RecentPropertiesAdapterAT(val context: Context, var recentItem: List<RecentPropertiesDataClass>) : RecyclerView.Adapter<RecentPropertiesAdapterAT.AddTaskViewHolder>()  {

    class AddTaskViewHolder(itemView: View) : ViewHolder(itemView){
        val hotelName = itemView.findViewById<TextView>(R.id.hotel_name)
        val hotelCover = itemView.findViewById<ImageView>(R.id.hotel_cover)
        val googleReview = itemView.findViewById<TextView>(R.id.google_review_text)
        val tripadReview = itemView.findViewById<TextView>(R.id.greview_review_text)
        val rating = itemView.findViewById<RatingBar>(R.id.ratingBar)
        val profile = itemView.findViewById<ImageView>(R.id.hotel_profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTaskViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_recent_properties,parent, false)
        return AddTaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recentItem.size
    }

    override fun onBindViewHolder(holder: AddTaskViewHolder, position: Int) {
        val item = recentItem[position]
        holder.hotelName.text = item.hotel_name
        holder.googleReview.text = item.Google_review.toString()
        holder.tripadReview.text = item.trip_advisor_review.toString()
        Glide.with(context).load(item.Cover_photo).into(holder.hotelCover)
        holder.rating.rating = item.hotel_stars.toFloat()
        Glide.with(context).load(item.hotel_logo).into(holder.profile)


        holder.itemView.setOnClickListener {
            val intent = Intent(context, HotelProfile::class.java)
            intent.putExtra("Name",item.hotel_name)
            intent.putExtra("image",item.Cover_photo)
            intent.putExtra("logo",item.hotel_logo)
            intent.putExtra("id",item.hotel_id)
            intent.putExtra("address",item.Address)
            intent.putExtra("google",item.Google_review.toString())
            intent.putExtra("trip",item.trip_advisor_review.toString())
            intent.putExtra("hotel_Star",item.hotel_stars)
            context.startActivity(intent)

        }

    }

    fun updateData(newItems: List<RecentPropertiesDataClass>) {
        recentItem = newItems
        notifyDataSetChanged()
    }
}