package com.retvence.rscoop.DashBoardIgniter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DataCollections.GetTaskData
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

        val hotelName = findViewById<TextView>(R.id.Egniter_Hotel_Name)
        val hotelImage = findViewById<ImageView>(R.id.Egniter_clienthotelimg)
        val hotelLogo = findViewById<ImageView>(R.id.Egniter_logoOfHotel)
        val name = findViewById<TextView>(R.id.Egniter_Hotel_Name2)
        val address = findViewById<TextView>(R.id.Egniter_hotel_country)
        val googleRating = findViewById<TextView>(R.id.googleRating)
        val tripAdvisorRating = findViewById<TextView>(R.id.trapadvisior)

        val Name = intent.getStringExtra("Name")
        val image = intent.getStringExtra("image")
        val logo = intent.getStringExtra("logo")
        val add = intent.getStringExtra("address")
        val googlereview = intent.getStringExtra("google")
        val tripAdvisor = intent.getStringExtra("trip")

        Glide.with(baseContext).load(image).into(hotelImage)
        Glide.with(baseContext).load(logo).into(hotelLogo)
        hotelName.text = Name
        name.text = Name
        address.text = add
        googleRating.text = googlereview
        tripAdvisorRating.text = tripAdvisor


        recyclerViewDate = findViewById(R.id.recyclerViewDate1)


        taskRecyclerView = findViewById(R.id.taskRecycler)
        taskRecyclerView.setHasFixedSize(true)
        taskRecyclerView.layoutManager = LinearLayoutManager(baseContext)


        setUpAdapter()
        setUpCalendar()
        getTask()

    }

    private fun getTask() {

        val id = intent.getStringExtra("id")

        val data = RetrofitBuilder.retrofitBuilder.individualTask(id!!)


        data.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                if (response.isSuccessful) {
                    val response = response.body()
                    adapter = HotelTaskAdapter(baseContext, response!!)
                    adapter.notifyDataSetChanged()
                    taskRecyclerView.adapter = adapter

                }else{
                    Toast.makeText(applicationContext,response.code(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {

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