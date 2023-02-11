package com.retvens.rscoop.RecentProperties

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rscoop.RecentProperties.CalendarDateModel
import com.retvens.rscoop.R

class CalendarAdapter(private val listener: (calendarDateModel: CalendarDateModel, position: Int) -> Unit):
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(){

    private val list = ArrayList<CalendarDateModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.date_layout,parent,false)
        return CalendarViewHolder(view)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(list[position])
        val itemList: CalendarDateModel = list[position]

//        val list: CalendarDateModel = list[position]
//        holder.calendarDay.text = list.calendarDay.toString()
//        holder.calendarDate.text = list.calendarDate.toString()
//        cardView.setOnClickListener {
//            listener.invoke(calendarDateModel, adapterPosition)
//        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(calendarDateModel: CalendarDateModel) {
            val calendarDay = itemView.findViewById<TextView>(R.id.tv_calendar_day)
            val calendarDate = itemView.findViewById<TextView>(R.id.tv_calendar_date)
            val cardView = itemView.findViewById<CardView>(R.id.card_calendar)

            calendarDay.text = calendarDateModel.calendarDay
            calendarDate.text = calendarDateModel.calendarDate

            val calendarAdapter = CalendarAdapter { calendarDateModel, position ->

                // Handle the click event here
                // You can access the CalendarDateModel with the `calendarDateModel` parameter
                // And the position of the item in the list with the `position` parameter

                cardView.setOnClickListener {
                    calendarDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    calendarDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    cardView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.sky_blue))
//                    listener.invoke(calendarDateModel, adapterPosition)
                }
            }

            itemView.setOnClickListener {
                if (calendarDateModel.isSelected) {
                calendarDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                calendarDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                cardView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.sky_blue))
                    } else {
                        calendarDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                        calendarDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                        cardView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
                    }
            }
        }
    }

    fun setData(calendarList: ArrayList<CalendarDateModel>) {
        list.clear()
        list.addAll(calendarList)
        notifyDataSetChanged()
    }
}
