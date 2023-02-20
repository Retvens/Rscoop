package com.retvence.rscoop.DashBoardIgniter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvens.rscoop.R

class SelectPropertyAdapter(val context : Context, var items: List<HotelsData>) : RecyclerView.Adapter<SelectPropertyAdapter.SelectPropertyViewHolder>() {

    private var listener: OnItemClickListener? = null
    var adapterPosition = -1

    class SelectPropertyViewHolder(itemView: View) : ViewHolder(itemView){
        val imageHotel = itemView.findViewById<ImageView>(R.id.hotel_add_task_img)
        val nameHotel = itemView.findViewById<TextView>(R.id.hotel_name_add_task)
        val starHotel = itemView.findViewById<TextView>(R.id.star_rate_number)
        val relativeLayout = itemView.findViewById<RelativeLayout>(R.id.selectPropertyRelative)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectPropertyViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view:View = inflater.inflate(R.layout.add_task_layout,parent,false)
        return SelectPropertyViewHolder(view)


    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SelectPropertyViewHolder, position: Int) {
        val items = items[position]
        holder.nameHotel.text = items.hotel_name
        holder.starHotel.text = items.hotel_stars
        Glide.with(context).load(items.Cover_photo).into(holder.imageHotel)

        holder.itemView.setOnClickListener {

            listener?.onItemClick(items)

            if (adapterPosition == position){
                holder.relativeLayout.background = context.getDrawable(R.drawable.rectangle_outline)
            }else{
                holder.relativeLayout.background = context.getDrawable(R.drawable.rectangle_background)
            }


        }
    }
    fun updateData(newData : List<HotelsData>){
        items = newData
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(item: HotelsData)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}