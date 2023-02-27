package com.retvence.rscoop.DashBoardIgniter

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
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

class HotelProfile : AppCompatActivity(), CalendarAdapter.onItemClickListener {

    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private val calendarList2 = ArrayList<CalendarDateModel>()

    lateinit var calendarAdapter: CalendarAdapter
    private lateinit var recyclerViewDate: RecyclerView
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var adapter:HotelTaskAdapter

    private lateinit var setD : TextView


    lateinit var call : ImageView
    lateinit var mail : ImageView
    lateinit var whatsapp : ImageView

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
        val star = findViewById<RatingBar>(R.id.ratingBar)

        val Name = intent.getStringExtra("Name")
        val image = intent.getStringExtra("image")
        val logo = intent.getStringExtra("logo")
        val add = intent.getStringExtra("address")
        val googlereview = intent.getStringExtra("google")
        val tripAdvisor = intent.getStringExtra("trip")
        val rating = intent.getIntExtra("hotel_Star",0)

        val hotel_Star = rating.toFloat()

        star.rating = hotel_Star

        Glide.with(baseContext).load(image).into(hotelImage)
        Glide.with(baseContext).load(logo).into(hotelLogo)
        hotelName.text = Name
        name.text = Name
        address.text = add
        googleRating.text = googlereview
        tripAdvisorRating.text = tripAdvisor

        findViewById<ImageView>(R.id.hotels_back_hp).setOnClickListener {
            onBackPressed()
        }


        call = findViewById(R.id.PhoneCall)
        mail = findViewById(R.id.maliLink)
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

        whatsapp = findViewById(R.id.whatsappLink)
        var fbIcon = findViewById<ImageView>(R.id.facebookLink)
        var linkIcon = findViewById<ImageView>(R.id.linkdinLink)
        var instaIcon = findViewById<ImageView>(R.id.instagramLink)
        var twitterIcon = findViewById<ImageView>(R.id.twitterLink)
        var pinterestIcon = findViewById<ImageView>(R.id.pinterestLink)
        var tripadViserIcon = findViewById<ImageView>(R.id.tripadivisorLink)
        var googleIcon = findViewById<ImageView>(R.id.googleLink)

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

        setD = findViewById(R.id.showDate)

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

                    setD.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            val originalData = response.toList()
                            val filterData = originalData.filter { c ->
                                c.Date.contains(s.toString(),ignoreCase = true)
                            }
                            adapter.updateData(filterData)
                        }

                        override fun afterTextChanged(s: Editable?) {
                        }
                    })

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
            calendarAdapter.setOnItemClickListener(this)
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
        calendarAdapter.setOnItemClickListener(this)
        calendarAdapter.setData(calendarList)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onItemClickDate(text: String) {
        setD.text = text
    }
}