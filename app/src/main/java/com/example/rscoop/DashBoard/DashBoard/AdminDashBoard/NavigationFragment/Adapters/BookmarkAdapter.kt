package com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerAdminView
import com.example.rscoop.DataCollections.HotelsData
import com.example.rscoop.DataCollections.HotelsLocation
import com.example.rscoop.R
import com.example.rscoop.RecentProperties.HotelsLocations

class BookmarkAdapter(val context: Context, var userList:List<HotelsData>):RecyclerView.Adapter<BookmarkAdapter.MyViewHolderClass>() {




    class MyViewHolderClass(itemview: View):RecyclerView.ViewHolder(itemview){

        var name = itemview.findViewById<TextView>(R.id.bookmark_Name)
        var image = itemview.findViewById<ImageView>(R.id.bookmark_Image)
        var rating = itemview.findViewById<TextView>(R.id.bookmark_Rating)

        init {


        }
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
            itemClicked(item)
        }
    }

    override fun getItemCount(): Int {
       return  userList.size
    }

    fun updateData(newItems: List<HotelsData>) {
        userList = newItems
        notifyDataSetChanged()
    }

    private fun itemClicked(item: HotelsData) {

        val intent = Intent(context, HotelsLocations::class.java)
        intent.putExtra("item_name", item.hotel_name)
        intent.putExtra("item_image", item.hotel_profile_photo)
        context.startActivity(intent)
    }


}