package com.retvence.rscoop.DashBoardIgniter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DataCollections.ResponseTask
import com.retvence.rscoop.DataCollections.StatusClass
import com.retvence.rscoop.RecentProperties.CalendarAdapter
import com.retvence.rscoop.RecentProperties.CalendarDateModel
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.file.Paths.get
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var dtDateMonth: TextView

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private val calendarList2 = ArrayList<CalendarDateModel>()
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    lateinit var calendarAdapter: CalendarAdapterFixed

    private lateinit var recyclerViewDate: RecyclerView


    lateinit var edit : CardView
    lateinit var done :CardView
    lateinit var getid:String
    lateinit var pending:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        findViewById<ImageView>(R.id.d_tasks_back_btn).setOnClickListener {
            onBackPressed()
        }

        dtDateMonth = findViewById(R.id.dt_date_month)
        recyclerViewDate = findViewById(R.id.recyclerViewDateDetail)


        val date = intent.getStringExtra("Date")
        val inputDate = SimpleDateFormat("dd MMMM yyyy",Locale.ENGLISH)
        val outputDate = SimpleDateFormat("MMMM yyyy",Locale.ENGLISH)

        val dateFormate = inputDate.parse(date)
        val dateFinale= outputDate.format(dateFormate)
        dtDateMonth.text = dateFinale


        edit = findViewById(R.id.edit_card)
        done = findViewById(R.id.done)
        pending = findViewById(R.id.pending)

        val image = findViewById<ImageView>(R.id.hotel_add_task_img)
        val name = findViewById<TextView>(R.id.hotel_name_add_task)
        val facebook = findViewById<TextView>(R.id.fb_post_deatail)
        val instagram = findViewById<TextView>(R.id.instaPost_detail)
        val google = findViewById<TextView>(R.id.google_post_detail)
        val linkedin = findViewById<TextView>(R.id.linkedin_post_detail)
        val tripad = findViewById<TextView>(R.id.tripad_post_detail)
        val twitter = findViewById<TextView>(R.id.twitter_post)
        val pinterest = findViewById<TextView>(R.id.pinterest_post_detail)


        getid = intent.getStringExtra("id").toString()
        val getimage = intent.getStringExtra("image")
        val getname = intent.getStringExtra("name")
        val getfacebook = intent.getStringExtra("facebook")
        val getinstagram = intent.getStringExtra("instagram")
        val getgoogle = intent.getStringExtra("google")
        val getlinkedin = intent.getStringExtra("linkedin")
        val gettripad = intent.getStringExtra("tripad")
        val gettwitter =intent.getStringExtra("twitter")
        val getpinterest = intent.getStringExtra("pinterest")

        Glide.with(baseContext).load(getimage).into(image)
        name.text = getname
        facebook.text = getfacebook
        instagram.text = getinstagram
        google.text = getgoogle
        linkedin.text = getlinkedin
        tripad.text = gettripad
        twitter.text = gettwitter
        pinterest.text = getpinterest

        edit.setOnClickListener {
            val intent = Intent(baseContext,DetailTaskEditActivity::class.java)
            intent.putExtra("id",getid)
            intent.putExtra("name",getname)
            intent.putExtra("image",getimage)
            intent.putExtra("facebook",getfacebook)
            intent.putExtra("instagram",getinstagram)
            intent.putExtra("google",getgoogle)
            intent.putExtra("linkedin",getlinkedin)
            intent.putExtra("tripad",gettripad)
            intent.putExtra("twitter",gettwitter)
            intent.putExtra("pinterest",getpinterest)
            startActivity(intent)

        }

        done.setOnClickListener {
            updateTask()
        }

        pending.setOnClickListener {
            PendingTask()
        }


        setUpAdapter()  // First We Set Adapter
        setUpCalendar() // Now Set Calendar


    }

    private fun PendingTask() {
        val data:String = "Pending"
        val id = getid
        val send = RetrofitBuilder.retrofitBuilder.updateStatus(id,StatusClass(data))
        send.enqueue(object : Callback<ResponseTask?> {
            override fun onResponse(call: Call<ResponseTask?>, response: Response<ResponseTask?>) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,response.code(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseTask?>, t: Throwable) {
                Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun updateTask() {
        val data:String = "Done"
        val id = getid
        val send = RetrofitBuilder.retrofitBuilder.updateStatus(id,StatusClass(data))
        send.enqueue(object : Callback<ResponseTask?> {
            override fun onResponse(call: Call<ResponseTask?>, response: Response<ResponseTask?>) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,response.code(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseTask?>, t: Throwable) {
                Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
            }
        })
    }

    /**
     * Setting up adapter for recyclerview
     */
    private fun setUpAdapter() {
        val date = intent.getStringExtra("Date")

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewDate)
        calendarAdapter = CalendarAdapterFixed { calendarDateModel: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }

           calendarAdapter.setData(calendarList2,"07")

        }
        recyclerViewDate.adapter = calendarAdapter

    }

    /**
      * Function to setup calendar for every month
     */
    private fun setUpCalendar()  {
        val date = intent.getStringExtra("Date")
        val inputDate = SimpleDateFormat("dd MMMM yyyy",Locale.ENGLISH)
        val outputDate = SimpleDateFormat("d",Locale.ENGLISH)

        val dateFormate = inputDate.parse(date)
        val dateFinale= outputDate.format(dateFormate)

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

        val position = calendarAdapter.setData(calendarList,dateFinale)
        if (position != RecyclerView.NO_POSITION){
            recyclerViewDate.scrollToPosition(position)

        }
    }

}