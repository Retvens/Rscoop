package com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerAdminView
import com.example.rscoop.DataCollections.HotelsData
import com.example.rscoop.R

class BookmarkAdapter(val context: Context, var userList:List<HotelsData>):RecyclerView.Adapter<BookmarkAdapter.MyViewHolderClass>() {



    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class MyViewHolderClass(itemview: View, listener:onItemClickListener):RecyclerView.ViewHolder(itemview){

        var name = itemview.findViewById<TextView>(R.id.bookmark_Name)
        var image = itemview.findViewById<ImageView>(R.id.bookmark_Image)
        var rating = itemview.findViewById<TextView>(R.id.bookmark_Rating)

        init {

            itemview.setOnClickListener {
                listener.onClick(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bookmarkscreen,parent,false)
        return MyViewHolderClass(view,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolderClass, position: Int) {

        holder.name.text = userList[position].hotel_name
        Glide.with(context).load(userList[position].hotel_profile_photo).into(holder.image)
        holder.rating.text = userList[position].hotel_stars.toString()
    }

    override fun getItemCount(): Int {
       return  userList.size
    }

    fun updateData(newItems: List<HotelsData>) {
        userList = newItems
        notifyDataSetChanged()
    }
}