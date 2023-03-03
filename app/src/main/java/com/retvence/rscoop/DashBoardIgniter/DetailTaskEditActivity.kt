package com.retvence.rscoop.DashBoardIgniter

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DataCollections.ResponseTask
import com.retvence.rscoop.DataCollections.UpdateTaskClass

import com.retvence.rscoop.RecentProperties.CalendarAdapter
import com.retvence.rscoop.RecentProperties.CalendarDateModel
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailTaskEditActivity : AppCompatActivity() {

    private lateinit var dteDateMonth: TextView
    private lateinit var dteCalendarNext: ImageView
    private lateinit var dteCalendarPrevious: ImageView

    private lateinit var facebook:EditText
    private lateinit var google:EditText
    private lateinit var instagram:EditText
    private lateinit var linkedin:EditText
    private lateinit var pinterest:EditText
    private lateinit var tripadvisor:EditText
    private lateinit var twitter:EditText
    lateinit var getId:String

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

        findViewById<ImageView>(R.id.add_tasks_back_btn_Edit).setOnClickListener {
            onBackPressed()
        }

        dteDateMonth = findViewById(R.id.dte_date_month)
        dteCalendarPrevious = findViewById(R.id.dte_calendar_previous)
        dteCalendarNext = findViewById(R.id.dte_calendar_next)

        setUpClickListener()
        setUpAdapter()  // First We Set Adapter
        setUpCalendar() // Now Set Calendar

        val save = findViewById<TextView>(R.id.updateSave)

        save.setOnClickListener {
            UpdateTask()
        }
        getId = intent.getStringExtra("id").toString()
        val img = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")


        val image = findViewById<ImageView>(R.id.hotel_add_task_img)
        val Name = findViewById<TextView>(R.id.hotel_name_add_task_Edit)
        facebook = findViewById(R.id.fb_post_deatail_Edit)
        google = findViewById(R.id.google_post_detail_Edit)
        instagram = findViewById(R.id.instaPost_detail_Edit)
        linkedin = findViewById(R.id.linkedin_post_detail_Edit)
        pinterest = findViewById(R.id.pinterest_post_detail_Edit)
        tripadvisor = findViewById(R.id.tripad_post_detail_Edit)
        twitter = findViewById(R.id.twitter_post_Edit)

        Glide.with(baseContext).load(img).into(image)
        Name.text = name

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun UpdateTask() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialoge_progress)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val getfacebook = intent.getStringExtra("facebook")
        val getinstagram = intent.getStringExtra("instagram")
        val getgoogle = intent.getStringExtra("google")
        val getlinkedin = intent.getStringExtra("linkedin")
        val gettripad = intent.getStringExtra("tripad")
        val gettwitter =intent.getStringExtra("twitter")
        val getpinterest = intent.getStringExtra("pinterest")



        val facebook = facebook.text.toString()
        val fbinput = facebook.split(",")
        val facebook1 = fbinput[0]
        val facebook2 = fbinput[1]
        val facebookString = "$facebook1 Video's and $facebook2 Posts"



        val Linkedin = linkedin.text.toString()
        val Linkinput = Linkedin.split(",")
        val Linkedin1 = Linkinput[0]
        val Linkedin2 = Linkinput[1]
        val LinkedinString = "$Linkedin1 Video's and $Linkedin2 Posts"


        val instagram = instagram.text.toString()
        val instaInput = instagram.split(",")
        val insta1 = instaInput[0]
        val insta2 = instaInput[1]
        val instagramString = "$insta1 Video's and $insta2 Posts"

        val twitter = twitter.text.toString()
        val twittInput = twitter.split(",")
        val twitter1 = twittInput[0]
        val twitter2 = twittInput[1]
        val twitterString = "$twitter1 Video's and $twitter2 Posts"

        val Pinterest = pinterest.text.toString()
        val pinterestInput = Pinterest.split(",")
        val pin1 = pinterestInput[0]
        val pin2 = pinterestInput[1]
        val pinterestString = "$pin1 Video's and $pin2 Posts"

        val GMB = tripadvisor.text.toString()
        val GMBString = "$GMB Reviews"

        val Google_review = google.text.toString()
        val googleString = "$Google_review Reviews"
        val id = getId

        val send =  RetrofitBuilder.retrofitBuilder.updateTask(id, UpdateTaskClass(
            facebookString,LinkedinString,instagramString,twitterString,pinterestString,GMBString,googleString
        ))

        send.enqueue(object : Callback<ResponseTask?> {
            override fun onResponse(call: Call<ResponseTask?>, response: Response<ResponseTask?>) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    dialog.dismiss()
                    Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG).show()
                    onBackPressed()
                }else{
                    Toast.makeText(applicationContext,response.code(),Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ResponseTask?>, t: Throwable) {
                Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        })
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


