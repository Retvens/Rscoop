package com.retvence.rscoop.DashBoardIgniter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DataCollections.TaskData
import com.retvence.rscoop.RecentProperties.CalendarAdapter
import com.retvence.rscoop.RecentProperties.CalendarDateModel
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class HotelProfile : AppCompatActivity() {

    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private val calendarList2 = ArrayList<CalendarDateModel>()

    lateinit var calendarAdapter: CalendarAdapter
    private lateinit var recyclerViewDate: RecyclerView
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var adapter:HotelTaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_profile)

        recyclerViewDate = findViewById(R.id.recyclerViewDate1)


        taskRecyclerView = findViewById(R.id.taskRecycler)
        taskRecyclerView.setHasFixedSize(true)
        taskRecyclerView.layoutManager = LinearLayoutManager(baseContext)


        setUpAdapter()
        setUpCalendar()

        getTask()

    }

    private fun getTask() {
        val data = RetrofitBuilder.retrofitBuilder.getTask()

        data.enqueue(object : Callback<List<TaskData>?> {
            override fun onResponse(
                call: Call<List<TaskData>?>,
                response: Response<List<TaskData>?>
            ) {
                val response = response.body()!!

                if(response != null) {
                    adapter = HotelTaskAdapter(baseContext, response)
                    adapter.notifyDataSetChanged()
                    taskRecyclerView.adapter = adapter



                }
            }

            override fun onFailure(call: Call<List<TaskData>?>, t: Throwable) {

            }
        })
    }

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