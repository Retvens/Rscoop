package com.retvence.rscoop.DashBoardIgniter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerHotelsView
import com.retvens.rscoop.R

class EgniterRecycler(val context: Context, var hotelList:List<HotelsData>):RecyclerView.Adapter<EgniterRecycler.MyclassViewgolder>() {
    class MyclassViewgolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var hotelImage = itemView.findViewById<ImageView>(R.id.hotelsImage)
        val hotelName = itemView.findViewById<TextView>(R.id.hotelName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyclassViewgolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hotelsrows,parent,false)
        return MyclassViewgolder(view)
    }

    override fun onBindViewHolder(holder: MyclassViewgolder, position: Int) {
        holder.hotelName.text = hotelList[position].hotel_name
        Glide.with(context).load(hotelList[position].hotel_profile_photo).into(holder.hotelImage)
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }
}