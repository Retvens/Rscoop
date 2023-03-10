package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvens.rscoop.R


class RecyclerHotelsView(val context: Context, var hotelList:List<HotelsData>): RecyclerView.Adapter<RecyclerHotelsView.MyclassViewHolder>() {

    class MyclassViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){

        var hotelImage = itemview.findViewById<ImageView>(R.id.hotelsImage)
        val hotelName = itemview.findViewById<TextView>(R.id.hotelName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyclassViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hotelsrows,parent,false)
        return MyclassViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyclassViewHolder, position: Int) {
        holder.hotelName.text = hotelList[position].hotel_name
        Glide.with(context).load(hotelList[position].hotel_logo).into(holder.hotelImage)



       holder.hotelImage.setOnClickListener {



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