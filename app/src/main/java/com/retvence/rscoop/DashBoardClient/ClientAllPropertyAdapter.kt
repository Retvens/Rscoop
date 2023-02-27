package com.retvence.rscoop.DashBoardClient

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retvence.rscoop.DashBoardIgniter.RecentPropertiesDataClass
import com.retvens.rscoop.R

class ClientAllPropertyAdapter(val context: Context, var hotelList: List<RecentPropertiesDataClass>): RecyclerView.Adapter<ClientAllPropertyAdapter.AllViewHolder>() {

    class AllViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val hotelName = itemView.findViewById<TextView>(R.id.hotel_name)
        val hotelCover = itemView.findViewById<ImageView>(R.id.hotel_cover)
        val googleReview = itemView.findViewById<TextView>(R.id.google_review_text)
        val tripadReview = itemView.findViewById<TextView>(R.id.greview_review_text)
        val rating = itemView.findViewById<RatingBar>(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_properties,parent,false)
        return AllViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
        val items = hotelList[position]
        holder.hotelName.text = items.hotel_name
        holder.googleReview.text = items.Google_review.toString()
        holder.tripadReview.text = items.trip_advisor_review.toString()
        Glide.with(context).load(items.Cover_photo).into(holder.hotelCover)
        holder.rating.rating = items.hotel_stars.toFloat()

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ViewAllTaskOfProperty::class.java)
            intent.putExtra("coverH",items.Cover_photo)
            intent.putExtra("logoH",items.hotel_logo)
            intent.putExtra("nameH",items.hotel_name)
            intent.putExtra("addH",items.Address)
            intent.putExtra("hotel_id",items.hotel_id)
            intent.putExtra("ratingH",items.hotel_stars)
            intent.putExtra("googleReview",items.Google_review)
            intent.putExtra("tripReview",items.trip_advisor_review)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    fun updateData(newItems: List<RecentPropertiesDataClass>) {
        hotelList = newItems
        notifyDataSetChanged()
    }
}