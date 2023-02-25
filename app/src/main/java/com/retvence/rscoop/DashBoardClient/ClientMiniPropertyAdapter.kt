package com.retvence.rscoop.DashBoardClient

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvens.rscoop.R

class ClientMiniPropertyAdapter(val context: Context, var hotelList:List<HotelsData>): RecyclerView.Adapter<ClientMiniPropertyAdapter.MiniViewHolder>() {

    class MiniViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var hotelImage = itemView.findViewById<ImageView>(R.id.hotelsImage)
        val hotelName = itemView.findViewById<TextView>(R.id.hotelName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hotelsrows,parent,false)
        return MiniViewHolder(view)
    }

    override fun onBindViewHolder(holder: MiniViewHolder, position: Int) {
        val items = hotelList[position]
        holder.hotelName.text = items.hotel_name
        Glide.with(context).load(items.Cover_photo).into(holder.hotelImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ViewAllTaskOfProperty::class.java)
            intent.putExtra("coverH",items.Cover_photo)
            intent.putExtra("logoH",items.hotel_logo)
            intent.putExtra("nameH",items.hotel_name)
            intent.putExtra("addH",items.Address)
            intent.putExtra("hotel_id",items.hotel_id)
            intent.putExtra("ratingH",items.hotel_stars)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    fun updateData(newItems: List<HotelsData>) {
        hotelList = newItems
        notifyDataSetChanged()
    }
}