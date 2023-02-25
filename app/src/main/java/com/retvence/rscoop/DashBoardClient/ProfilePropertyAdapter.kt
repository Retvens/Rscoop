package com.retvence.rscoop.DashBoardClient

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvens.rscoop.R

class ProfilePropertyAdapter(val context: Context,val itemC : List<HotelsData>) : RecyclerView.Adapter<ProfilePropertyAdapter.ProfileViewHolder>() {

    class ProfileViewHolder(itemView: View) : ViewHolder(itemView){
        val imageHotel = itemView.findViewById<ImageView>(R.id.hotel_add_task_img)
        val nameHotel = itemView.findViewById<TextView>(R.id.hotel_name_add_task)
        val starHotel = itemView.findViewById<TextView>(R.id.star_rate_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view:View = inflater.inflate(R.layout.add_task_layout,parent,false)
        return ProfileViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemC.size
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val items = itemC[position]
        holder.nameHotel.text = items.hotel_name
        holder.starHotel.text = items.hotel_stars.toString()
        Glide.with(context).load(items.Cover_photo).into(holder.imageHotel)

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ViewAllTaskOfProperty::class.java)
            intent.putExtra("coverH",items.Cover_photo)
            intent.putExtra("logoH",items.hotel_logo)
            intent.putExtra("nameH",items.hotel_name)
            intent.putExtra("addH",items.Address)
            intent.putExtra("ratingH",items.hotel_stars)
            context.startActivity(intent)
        }

    }
}