package com.retvence.rscoop.DashBoardClient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
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

    private lateinit var coverImg: ImageView
    private lateinit var nameHotel: TextView
    private lateinit var star: TextView

    private lateinit var facebookText : TextView
    private lateinit var google: TextView
    private lateinit var insta: TextView
    private lateinit var linkedin: TextView
    private lateinit var pinterest: TextView
    private lateinit var tripod: TextView
    private lateinit var twitter: TextView


    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private val calendarList2 = ArrayList<CalendarDateModel>()
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_detail_task)

        findViewById<ImageView>(R.id.detail_task_client_back_btn).setOnClickListener {
           onBackPressed()
        }

        coverImg = findViewById(R.id.hotel_add_task_client_img)
        nameHotel = findViewById(R.id.hotel_name_add_task_client)
        star = findViewById(R.id.star_rate_number_client)

        facebookText = findViewById(R.id.fb_post_detail_client)
        google = findViewById(R.id.google_post_detail_client)
        insta = findViewById(R.id.instaPost_detail_client)
        linkedin = findViewById(R.id.linkedin_post_detail_client)
        pinterest = findViewById(R.id.pinterest_post_detail_client)
        tripod = findViewById(R.id.tripad_post_detail_client)
        twitter = findViewById(R.id.twitter_post_detail_client)

        val cover = intent.getStringExtra("imageCover")
        val name = intent.getStringExtra("nameHotel")
        val facebookT = intent.getStringExtra("facebook")
        val instagramT = intent.getStringExtra("instagram")
        val googleT = intent.getStringExtra("google")
        val linkedinT = intent.getStringExtra("linkedin")
        val tripadT = intent.getStringExtra("tripad")
        val twitterT =intent.getStringExtra("twitter")
        val pinterestT = intent.getStringExtra("pinterest")

        Glide.with(baseContext).load(cover).into(coverImg)
        nameHotel.text = name
        facebookText.text = facebookT
        insta.text = instagramT
        google.text  =  googleT
        linkedin.text = linkedinT
        tripod.text = tripadT
        twitter.text = twitterT
        pinterest.text = pinterestT


        recyclerViewDate = findViewById(R.id.recyclerViewDateDetailClient)
        dtcCalendarNext = findViewById(R.id.dtc_calendar_next)
        dtcCalendarPrevious = findViewById(R.id.dtc_calendar_previous)
        dtcDateMonth = findViewById(R.id.dtc_date_month)
        pending = findViewById(R.id.pendingClient)
        done = findViewById(R.id.done_client)

        done.setOnClickListener {
            Toast.makeText(this@ClientDetailTaskActivity,"Done",Toast.LENGTH_SHORT)
                .show()
        }

        pending.setOnClickListener {
            Toast.makeText(this@ClientDetailTaskActivity,"Pending",Toast.LENGTH_SHORT)
                .show()
        }
        val status = intent.getStringExtra("status").toString()

        if (status == "Done"){
            pending.visibility = View.GONE
        }else{
            done.visibility = View.GONE
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