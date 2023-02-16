package com.retvence.rscoop.DashBoardIgniter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.RecentProperties.CalendarAdapter
import com.retvence.rscoop.RecentProperties.CalendarDateModel
import com.retvens.rscoop.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNewTaskActivity : AppCompatActivity() {

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private val calendarList2 = ArrayList<CalendarDateModel>()

    private lateinit var recyclerViewDate: RecyclerView
    private lateinit var calendarAdapter: CalendarAdapter

    private lateinit var recyclerViewSelectProperty: RecyclerView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var search: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_task)

        recyclerViewDate = findViewById(R.id.recyclerViewDate)
        recyclerViewDate.setHasFixedSize(true)
        recyclerViewDate.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.add_tasks_back_btn).setOnClickListener {
            startActivity(Intent(this, AdminDashBoard::class.java))
//            finish()
        }
            setUpCalendar()
            setUpAdapter()

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
