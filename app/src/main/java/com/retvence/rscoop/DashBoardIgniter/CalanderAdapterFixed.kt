package com.retvence.rscoop.DashBoardIgniter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.retvence.rscoop.RecentProperties.CalendarDateModel
import com.retvens.rscoop.R

class CalendarAdapterFixed(private val listener: (calendarDateModel: CalendarDateModel, position: Int) -> Unit):
    RecyclerView.Adapter<CalendarAdapterFixed.CalendarFixedViewHolder>() {

    private val list = ArrayList<CalendarDateModel>()

    private var pose = 0

    interface onItemClickListener{
        fun onItemClickDate(text:String)
    }

    private var mListener: onItemClickListener? = null

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarFixedViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.date_layout, parent, false)
        return CalendarFixedViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarFixedViewHolder, position: Int) {
        val itemList = list[position]
        holder.calendarDay.text = itemList.calendarDay
        holder.calendarDate.text = itemList.calendarDate

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context,itemList.calendarYear,Toast.LENGTH_SHORT)
                .show()
        }

        if (position == pose) {
            holder.calendarDay.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
            holder.calendarDate.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
            holder.linear.background =
                holder.itemView.context.getDrawable(R.drawable.round_corner_selected)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CalendarFixedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val calendarDay = itemView.findViewById<TextView>(R.id.tv_calendar_day)
        val calendarDate = itemView.findViewById<TextView>(R.id.tv_calendar_date)
        val linear = itemView.findViewById<LinearLayout>(R.id.linear_calendar)
    }

    fun setData(calendarList: ArrayList<CalendarDateModel>, itemId : String) : Int {
        list.clear()
        list.addAll(calendarList)
        notifyDataSetChanged()
        for(i in list.indices){
            val item = list[i]
            if (item.calendarDate == itemId){
                pose = i
                return i
            }
        }
        return RecyclerView.NO_POSITION
    }
}