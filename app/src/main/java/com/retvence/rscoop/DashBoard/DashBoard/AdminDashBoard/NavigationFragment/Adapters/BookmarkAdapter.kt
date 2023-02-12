package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.Adapters

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
import com.retvens.rscoop.RecentProperties.HotelsLocations

class BookmarkAdapter(val context: Context, var userList:List<HotelsData>):RecyclerView.Adapter<BookmarkAdapter.MyViewHolderClass>() {




    class MyViewHolderClass(itemview: View):RecyclerView.ViewHolder(itemview){

        var name = itemview.findViewById<TextView>(R.id.bookmark_Name)
        var image = itemview.findViewById<ImageView>(R.id.bookmark_Image)
        var rating = itemview.findViewById<TextView>(R.id.bookmark_Rating)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bookmarkscreen,parent,false)
        return MyViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: MyViewHolderClass, position: Int) {

        val item = userList[position]

        holder.name.text = userList[position].hotel_name
        Glide.with(context).load(userList[position].hotel_profile_photo).into(holder.image)
        holder.rating.text = userList[position].hotel_stars.toString()

        holder.itemView.setOnClickListener {
                val intent = Intent(context,HotelsLocations::class.java)
                intent.putExtra("Name",item.hotel_name)
                intent.putExtra("image",item.Cover_photo)
                intent.putExtra("logo",item.hotel_profile_photo)

            for (location in item.hotel_location){
                intent.putExtra("latitude",18.921729)
                intent.putExtra("longitude",72.833031)

            }

                context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
       return  userList.size
    }

    fun updateData(newItems: List<HotelsData>) {
        userList = newItems
        notifyDataSetChanged()
    }


}