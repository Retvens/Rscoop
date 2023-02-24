package com.retvence.rscoop.DashBoardIgniter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.retvence.rscoop.RecentProperties.CalendarAdapter
import com.retvence.rscoop.RecentProperties.CalendarDateModel
import com.retvens.rscoop.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailTaskEditActivity : AppCompatActivity() {

    private lateinit var dteDateMonth: TextView
    private lateinit var dteCalendarNext: ImageView
    private lateinit var dteCalendarPrevious: ImageView

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val calendarList2 = ArrayList<CalendarDateModel>()

    lateinit var calendarAdapter: CalendarAdapter

    lateinit var recyclerEdit : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task_edit)
        recyclerEdit = findViewById(R.id.recyclerViewDateEdit)

        dteDateMonth = findViewById(R.id.dte_date_month)
        dteCalendarPrevious = findViewById(R.id.dte_calendar_previous)
        dteCalendarNext = findViewById(R.id.dte_calendar_next)

        setUpClickListener()
        setUpAdapter()  // First We Set Adapter
        setUpCalendar() // Now Set Calendar
    }

    private fun setUpClickListener() {
        dteCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        dteCalendarPrevious.setOnClickListener {
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
        snapHelper.attachToRecyclerView(recyclerEdit)
        calendarAdapter = CalendarAdapter { calendarDateModel: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }
            calendarAdapter.setData(calendarList2)
        }
        recyclerEdit.adapter = calendarAdapter
    }

    /**
     * Function to setup calendar for every month
     */
    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        dteDateMonth.text = sdf.format(cal.time)
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