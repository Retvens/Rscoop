package com.retvence.rscoop.DashBoardClient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.retvence.rscoop.RecentProperties.CalendarAdapter
import com.retvence.rscoop.RecentProperties.CalendarDateModel
import com.retvens.rscoop.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ClientDetailTaskActivity : AppCompatActivity() {

    private lateinit var dtcDateMonth: TextView
    private lateinit var dtcCalendarNext: ImageView
    private lateinit var dtcCalendarPrevious: ImageView
    private lateinit var recyclerViewDate: RecyclerView

    private lateinit var pending: CardView
    private lateinit var done: CardView
    private lateinit var back: ImageView

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private val calendarList2 = ArrayList<CalendarDateModel>()
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_detail_task)

        recyclerViewDate = findViewById(R.id.recyclerViewDateDetailClient)
        dtcCalendarNext = findViewById(R.id.dtc_calendar_next)
        dtcCalendarPrevious = findViewById(R.id.dtc_calendar_previous)
        dtcDateMonth = findViewById(R.id.dtc_date_month)
        pending = findViewById(R.id.pendingClient)
        done = findViewById(R.id.done_client)

        back = findViewById(R.id.detail_task_client_back_btn)
        back.setOnClickListener {
            startActivity(Intent(this@ClientDetailTaskActivity,ViewAllTaskOfProperty::class.java))
        }

        done.setOnClickListener {
            Toast.makeText(this@ClientDetailTaskActivity,"Done",Toast.LENGTH_SHORT)
                .show()
        }

        pending.setOnClickListener {
            Toast.makeText(this@ClientDetailTaskActivity,"Pending",Toast.LENGTH_SHORT)
                .show()
        }

        setUpClickListener()
        setUpAdapter()  // First We Set Adapter
        setUpCalendar() // Now Set Calendar
    }

    private fun setUpClickListener() {
        dtcCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        dtcCalendarPrevious.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            if (cal == currentDate)
                setUpCalendar()
            else
                setUpCalendar()
        }
    }
    /**
     * Setting up adapter for recyclerview
     */
    private fun setUpAdapter() {
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewDate)
        calendarAdapter = CalendarAdapter { calendarDateModel: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }

            calendarAdapter.setData(calendarList2)
        }
        recyclerViewDate.adapter = calendarAdapter
    }

    /**
     * Function to setup calendar for every month
     */
    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        dtcDateMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)
            calendarList.add(CalendarDateModel(monthCalendar.time))
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendarList2.clear()
        calendarList2.addAll(calendarList)
        calendarAdapter.setData(calendarList)
    }


}