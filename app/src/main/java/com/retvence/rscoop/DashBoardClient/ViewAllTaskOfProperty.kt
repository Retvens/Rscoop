package com.retvence.rscoop.DashBoardClient

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DataCollections.GetTaskData
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

class ViewAllTaskOfProperty : AppCompatActivity() {

    private lateinit var textDateMonth: TextView
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

    lateinit var call : ImageView
    lateinit var mail : ImageView
    lateinit var whatsapp : ImageView

    private lateinit var nameTop : TextView
    private lateinit var nameMid : TextView
    private lateinit var address : TextView
    private lateinit var ratingBar: RatingBar
    lateinit var clientCover : ImageView
    lateinit var clientProfile : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_task_of_property)

        address = findViewById(R.id.Client_hotel_country)
        ratingBar = findViewById(R.id.ratingBar_client)
        nameTop = findViewById(R.id.client_Hotel_Name)
        nameMid = findViewById(R.id.Client_Hotel_Name2)
        clientProfile = findViewById(R.id.client_hotel_profile)
        clientCover = findViewById(R.id.client_hotel_cover)

        textDateMonth = findViewById(R.id.c_date_month)
        ivCalendarNext = findViewById(R.id.c_calendar_next)
        ivCalendarPrevious = findViewById(R.id.c_calendar_previous)
        recyclerViewDate = findViewById(R.id.recyclerViewDateClient)

        recyclerView = findViewById(R.id.recycler_Tasks_Client)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        shimmerFrameLayout = findViewById(R.id.shimmer_tasks_view_container_client)

        call = findViewById(R.id.PhoneCall_client)
        mail = findViewById(R.id.mali_client)
        call.setOnClickListener {
            val uriCall : Uri = Uri.parse("tel:"+"7905845935")
            val intentCall = Intent(Intent.ACTION_DIAL,uriCall)
            startActivity(intentCall)
        }
        mail.setOnClickListener {
            val uriMail: Uri = Uri.parse("mailto:"+"arjungupta0817@gmail.com")
            val intentMail = Intent(Intent.ACTION_SENDTO,uriMail)
            intentMail.putExtra(Intent.EXTRA_SUBJECT,"test")
            startActivity(intentMail)
        }

        whatsapp = findViewById(R.id.whatsapp_client)
        var fbIcon = findViewById<ImageView>(R.id.facebook_client)
        var linkIcon = findViewById<ImageView>(R.id.linkdin_client)
        var instaIcon = findViewById<ImageView>(R.id.instagram_client)
        var twitterIcon = findViewById<ImageView>(R.id.twitter_client)
        var pinterestIcon = findViewById<ImageView>(R.id.pinterest_client)
        var tripadViserIcon = findViewById<ImageView>(R.id.tripadivisor_client)
        var googleIcon = findViewById<ImageView>(R.id.google_client)

        whatsapp.setOnClickListener {
            val uri : Uri = Uri.parse("http://whatsapp.com")
            val intent = Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }
        fbIcon.setOnClickListener{
            val uriForFB : Uri = Uri.parse("https://facebook.com")
            val openFB = Intent(Intent.ACTION_VIEW,uriForFB)
            startActivity(openFB)
        }
        instaIcon.setOnClickListener {
            val uriForinsta: Uri = Uri.parse("http://instagram.com")
            val forinsta = Intent(Intent.ACTION_VIEW, uriForinsta)
            startActivity(forinsta)
        }
        linkIcon.setOnClickListener {
            val uriForLink : Uri = Uri.parse("https://linkedin.com")
            startActivity(Intent(Intent.ACTION_VIEW,uriForLink))
        }
        twitterIcon.setOnClickListener{
            val uriFortwitter : Uri = Uri.parse("https://twitter.com")
            val openTwitter = Intent(Intent.ACTION_VIEW,uriFortwitter)
            startActivity(openTwitter)
        }
        pinterestIcon.setOnClickListener{
            val uriForPinterest : Uri = Uri.parse("https://pinterest.com")
            val openPint = Intent(Intent.ACTION_VIEW,uriForPinterest)
            startActivity(openPint)
        }
        tripadViserIcon.setOnClickListener{
            val uriForTripod : Uri = Uri.parse("https://tripadvisor.com")
            val openTripadVisor = Intent(Intent.ACTION_VIEW,uriForTripod)
            startActivity(openTripadVisor)
        }
        googleIcon.setOnClickListener {
            val uriForGogle : Uri = Uri.parse("https://google.com")
            val openGoogle = Intent(Intent.ACTION_VIEW,uriForGogle)
            startActivity(openGoogle)
        }

        //getting data from intent
        val hotelName = intent.getStringExtra("nameH")
        val hotelCover = intent.getStringExtra("coverH")
        val addressHotel = intent.getStringExtra("addH")
        val rate = intent.getStringExtra("ratingH")
        val logoHotel = intent.getStringExtra("logoH")

        nameTop.text = hotelName
        Glide.with(baseContext).load(hotelCover).into(clientCover)
        nameMid.text = hotelName
        Glide.with(baseContext).load(logoHotel).into(clientProfile)
        address.text = addressHotel.toString()
        ratingBar.rating = rate?.toFloat()!!

        setUpClickListener()
        setUpAdapter()
        setUpCalendar()
        allTaskData()
    }

    private fun allTaskData() {
        val getData = RetrofitBuilder.retrofitBuilder.getTask()
        getData.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    recentrecycler = RecentRecycler(this@ViewAllTaskOfProperty,response)
                    recentrecycler.notifyDataSetChanged()
                    recyclerView.adapter = recentrecycler

                    shimmerFrameLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                }else{
                    Toast.makeText(this@ViewAllTaskOfProperty,response.code(),Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {
                Toast.makeText(this@ViewAllTaskOfProperty,t.localizedMessage,Toast.LENGTH_SHORT)
                    .show()
            }
        })
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
            adapter.setData(calendarList2)
        }
        recyclerViewDate.adapter = adapter
    }

    /**
     * Function to setup calendar for every month
     */
    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        textDateMonth.text = sdf.format(cal.time)
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
        adapter.setData(calendarList)
    }


}