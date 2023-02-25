package com.retvens.rscoop.RecentProperties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DashBoardIgniter.SelectPropertyAdapter
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvence.rscoop.DataCollections.TaskData
import com.retvence.rscoop.RecentProperties.CalendarAdapter
import com.retvence.rscoop.RecentProperties.CalendarDateModel
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ViewAllTasks : AppCompatActivity(), CalendarAdapter.onItemClickListener {

    private lateinit var taskDate : String

    private lateinit var tvDateMonth: TextView
    private lateinit var ivCalendarNext: ImageView
    private lateinit var ivCalendarPrevious: ImageView

    private lateinit var recyclerViewDate: RecyclerView
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private lateinit var adapter: CalendarAdapter
    private val calendarList2 = ArrayList<CalendarDateModel>()


    private lateinit var recyclerView: RecyclerView
    private lateinit var recentrecycler: RecentRecycler

    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    lateinit var shimmerLayout: LinearLayout
    private lateinit var search:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_tasks)

        val backbtn = findViewById<ImageView>(R.id.tasks_back_btn)
        backbtn.setOnClickListener {
           onBackPressed()
        }

        tvDateMonth = findViewById(R.id.tv_date_month)
        recyclerViewDate = findViewById(R.id.recyclerViewDate)
        ivCalendarNext = findViewById(R.id.iv_calendar_next)
        ivCalendarPrevious = findViewById(R.id.iv_calendar_previous)

        recyclerView = findViewById(R.id.recycler_Tasks)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //search
        search = findViewById(R.id.search_tasks)

        shimmerFrameLayout = findViewById(R.id.shimmer_tasks_view_container)
        shimmerLayout = findViewById(R.id.shimmer_layout_tasks)

        setUpClickListener()
        setUpAdapter()
        setUpCalendar()
        allTaskData()
    }


        override fun onItemClickDate(text:String) {
            taskDate = text.toString()
            Toast.makeText(this, taskDate, Toast.LENGTH_SHORT)
                .show()
        }

    /**
     * Set up click listener
     */

    private fun setUpClickListener() {
        ivCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        ivCalendarPrevious.setOnClickListener {
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
        adapter = CalendarAdapter { calendarDateModel: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }
            adapter.setOnItemClickListener(this@ViewAllTasks)
            adapter.setData(calendarList2)
        }
        recyclerViewDate.adapter = adapter
    }

    /**
     * Function to setup calendar for every month
     */
    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        tvDateMonth.text = sdf.format(cal.time)
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
        adapter.setOnItemClickListener(this@ViewAllTasks)
        adapter.setData(calendarList)
    }

    private fun allTaskData() {
        val allTask = RetrofitBuilder.retrofitBuilder.getTask()

        allTask.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                val response = response.body()!!
                recentrecycler = RecentRecycler(baseContext,response)
                recentrecycler.notifyDataSetChanged()
                recyclerView.adapter = recentrecycler

                shimmerLayout.setVisibility(View.GONE)
                recyclerView.visibility = View.VISIBLE

                search.addTextChangedListener(object :TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        val originalData = response.toList()
                        val filterData = originalData.filter { item ->
                            item.hotel_name.contains(p0.toString(),ignoreCase = true)
                        }

                        recentrecycler.updateData(filterData)
                    }

                    override fun afterTextChanged(p0: Editable?) {

                    }

                })

            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {

            }
        })
    }
}