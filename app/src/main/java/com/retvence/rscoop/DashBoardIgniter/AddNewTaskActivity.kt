package com.retvence.rscoop.DashBoardIgniter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvence.rscoop.DataCollections.ResponseTask
import com.retvence.rscoop.DataCollections.TaskData
import com.retvence.rscoop.RecentProperties.CalendarAdapter
import com.retvence.rscoop.RecentProperties.CalendarDateModel
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNewTaskActivity : AppCompatActivity(), SelectPropertyAdapter.OnItemClickListener,
    CalendarAdapter.onItemClickListener {

    private lateinit var atDateMonth: TextView
    private lateinit var atCalendarNext: ImageView
    private lateinit var atCalendarPrevious: ImageView

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private val calendarList2 = ArrayList<CalendarDateModel>()
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    lateinit var calendarAdapter: CalendarAdapter

    private lateinit var recyclerViewDate: RecyclerView

    private lateinit var recyclerViewSelectProperty: RecyclerView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    lateinit var selectAdapter : SelectPropertyAdapter
    private lateinit var searchProp: EditText

    lateinit var hotelName : String
    lateinit var hotelDate : String
    lateinit var hotelImage :String
    lateinit var hotelId: String
    lateinit var ownerId:String

    private lateinit var fbPost: EditText
    private lateinit var googlePost: EditText
    private lateinit var instaPost: EditText
    private lateinit var linkedinPost: EditText
    private lateinit var pinterestPost: EditText
    private lateinit var tripadPost: EditText
    private lateinit var twitterPost: EditText
    private lateinit var viewAllAddedTask: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_task)

        recyclerViewDate = findViewById(R.id.recyclerViewDateNewTask)

        searchProp = findViewById(R.id.search_add_all_tasks)
        shimmerFrameLayout = findViewById(R.id.recycler_add_all_task_shimmer)
        recyclerViewSelectProperty = findViewById(R.id.recycler_add_all_task)
        recyclerViewSelectProperty.setHasFixedSize(true)
        recyclerViewSelectProperty.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        fbPost = findViewById(R.id.fb_post)
        googlePost = findViewById(R.id.google_post)
        instaPost = findViewById(R.id.instaPost)
        linkedinPost = findViewById(R.id.linkedin_post)
        pinterestPost = findViewById(R.id.pinterest_post)
        tripadPost = findViewById(R.id.tripad_post)
        twitterPost = findViewById(R.id.twitter_post)

        atCalendarNext = findViewById(R.id.at_calendar_next)
        atCalendarPrevious = findViewById(R.id.at_calendar_previous)
        atDateMonth = findViewById(R.id.at_date_month)

        viewAllAddedTask = findViewById(R.id.view_all_added_tasks)
        viewAllAddedTask.setOnClickListener {
            startActivity(Intent(this@AddNewTaskActivity,AddNewTaskRecentProperty::class.java))
        }

        findViewById<ImageView>(R.id.add_tasks_back_btn).setOnClickListener {
            onBackPressed()
        }

        findViewById<CardView>(R.id.save_property).setOnClickListener {
            createData()
        }
        getHotelData()
        setUpClickListener()
        setUpAdapter()  // First We Set Adapter
        setUpCalendar() // Now Set Calendar
    }

    override fun onItemClickDate(text:String){
        hotelDate = text
        Toast.makeText(this,hotelDate,Toast.LENGTH_SHORT)
            .show()
    }
    override fun onItemClick(item: HotelsData) {
        hotelName = item.hotel_name
        hotelImage = item.Cover_photo
        hotelId  = item.hotel_id
        ownerId = item.owner_id

    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
    private fun createData() {

        val facebook = fbPost.text.toString()
        val Linkedin = linkedinPost.text.toString()
        val instagram = instaPost.text.toString()
        val twitter = twitterPost.text.toString()
        val Pinterest = pinterestPost.text.toString()
        val GMB = tripadPost.text.toString()
        val Google_review = googlePost.text.toString()

        val data = TaskData(hotelName,ownerId,hotelDate,facebook,Linkedin,instagram,twitter,Pinterest,GMB,Google_review,
            hotelImage,hotelId,"Pending")

        val send = RetrofitBuilder.retrofitBuilder.createSocialMeadia(data)
        send.enqueue(object : Callback<ResponseTask?> {
            override fun onResponse(call: Call<ResponseTask?>, response: Response<ResponseTask?>) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    Toast.makeText(applicationContext,response.message.toString(),Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseTask?>, t: Throwable) {
                Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
            }
        })



    }

    private fun getHotelData() {
        val retrofit = RetrofitBuilder.retrofitBuilder.getHotel("")
        retrofit.enqueue(object : Callback<List<HotelsData>?> {
            override fun onResponse(
                call: Call<List<HotelsData>?>,
                response: Response<List<HotelsData>?>
            ) {
                val response = response.body()!!
                selectAdapter = SelectPropertyAdapter(this@AddNewTaskActivity,response)
                selectAdapter.notifyDataSetChanged()
                recyclerViewSelectProperty.adapter = selectAdapter
                selectAdapter.setOnItemClickListener(this@AddNewTaskActivity)
                shimmerFrameLayout.visibility = View.GONE

                searchProp.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val originalData = response.toList()
                        val filterData = originalData.filter { data ->
                            data.hotel_name.contains(s.toString(), ignoreCase = true)
                        }
                        selectAdapter.updateData(filterData)
                    }
                    override fun afterTextChanged(s: Editable?) {

                    }
                })

            }

            override fun onFailure(call: Call<List<HotelsData>?>, t: Throwable) {
                Toast.makeText(this@AddNewTaskActivity,t.localizedMessage,Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    /**
     * Set up click listener
     */

    private fun setUpClickListener() {
        atCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        atCalendarPrevious.setOnClickListener {
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

                calendarAdapter.setOnItemClickListener(this@AddNewTaskActivity)
                calendarAdapter.setData(calendarList2)
            }
            recyclerViewDate.adapter = calendarAdapter
        }

        /**
         * Function to setup calendar for every month
         */
         private fun setUpCalendar() {
            val calendarList = ArrayList<CalendarDateModel>()
            atDateMonth.text = sdf.format(cal.time)
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
            calendarAdapter.setOnItemClickListener(this@AddNewTaskActivity)
            calendarAdapter.setData(calendarList)
        }



}
