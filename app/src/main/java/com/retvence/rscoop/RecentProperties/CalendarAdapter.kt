package com.retvence.rscoop.RecentProperties

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.retvens.rscoop.R

class CalendarAdapter( private val listener: (calendarDateModel: CalendarDateModel, position: Int) -> Unit):
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(){

    private val list = ArrayList<CalendarDateModel>()

    var adapterPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.date_layout,parent,false)
        return CalendarViewHolder(view)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val itemList = list[position]
        holder.calendarDay.text = itemList.calendarDay
        holder.calendarDate.text = itemList.calendarDate

        if (position == adapterPosition){
            holder.calendarDay.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.calendarDate.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.linear.background = holder.itemView.context.getDrawable(R.drawable.round_corner_selected)

        }else {
            holder.calendarDay.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            holder.calendarDate.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            holder.linear.background = holder.itemView.context.getDrawable(R.drawable.round_corner)
        }

        holder.itemView.setOnClickListener {
            adapterPosition = position
            notifyItemRangeChanged(0, list.size)
//            listener.invoke(itemList, adapterPosition)

        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val calendarDay = itemView.findViewById<TextView>(R.id.tv_calendar_day)
        val calendarDate = itemView.findViewById<TextView>(R.id.tv_calendar_date)
        val linear = itemView.findViewById<LinearLayout>(R.id.linear_calendar)
    }
    fun setData(calendarList: ArrayList<CalendarDateModel>) {
        list.clear()
        list.addAll(calendarList)
        notifyDataSetChanged()
    }
}
