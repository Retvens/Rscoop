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
import com.retvence.rscoop.DataCollections.TaskData
import com.retvence.rscoop.RecentProperties.CalendarAdapter
import com.retvence.rscoop.RecentProperties.CalendarDateModel
import com.retvens.rscoop.R
import com.retvens.rscoop.RecentProperties.RecentPropertiesView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class AddNewTaskActivity : AppCompatActivity() {

    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private val calendarList2 = ArrayList<CalendarDateModel>()

    lateinit var calendarAdapter: CalendarAdapter

    private lateinit var recyclerViewDate: RecyclerView

    private lateinit var recyclerViewSelectProperty: RecyclerView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    lateinit var selectAdapter : SelectPropertyAdapter
    private lateinit var searchProp: EditText


    private lateinit var fbPost: TextView
    private lateinit var googlePost: TextView
    private lateinit var instaPost: TextView
    private lateinit var linkedinPost: TextView
    private lateinit var pinterestPost: TextView
    private lateinit var tripadPost: TextView
    private lateinit var twitterPost: TextView
    private lateinit var viewAllAddedTask: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_task)

        recyclerViewDate = findViewById(R.id.recyclerViewDate)

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

        viewAllAddedTask = findViewById(R.id.view_all_added_tasks)
        viewAllAddedTask.setOnClickListener {
            startActivity(Intent(this@AddNewTaskActivity,AddNewTaskRecentProperty::class.java))
        }

        findViewById<ImageView>(R.id.add_tasks_back_btn).setOnClickListener {
            startActivity(Intent(this, AdminDashBoard::class.java))
//            finish()
        }


        findViewById<CardView>(R.id.save_property).setOnClickListener {
            createData()
        }
        getHotelData()
        setUpAdapter()  // First We Set Adapter
        setUpCalendar() // Now Set Calendar
    }

    private fun createData() {

        val facebook = fbPost.text.toString()
        val Linkedin = linkedinPost.text.toString()
        val instagram = instaPost.text.toString()
        val twitter = twitterPost.text.toString()
        val Pinterest = pinterestPost.text.toString()
        val GMB = tripadPost.text.toString()
        val Google_review = googlePost.text.toString()

        val upload = RetrofitBuilder.retrofitBuilder.createSocialMeadia(TaskData("","New Hotel","20/10/30",
            facebook, Linkedin,instagram,twitter,Pinterest,GMB,Google_review,""))
        upload.enqueue(object : Callback<TaskData?> {
            override fun onResponse(call: Call<TaskData?>, response: Response<TaskData?>) {

                Log.d("post",response.code().toString())
                Log.d("post","-----------------------")
                if (response.isSuccessful){
                    Toast.makeText(this@AddNewTaskActivity,response.code().toString(),Toast.LENGTH_LONG)
                        .show()
                }else{
                    Log.e("post", response.errorBody().toString())
                    Toast.makeText(this@AddNewTaskActivity,response.code().toString(),Toast.LENGTH_LONG)
                        .show()
                }
            }
            override fun onFailure(call: Call<TaskData?>, t: Throwable) {
                Toast.makeText(this@AddNewTaskActivity,t.localizedMessage,Toast.LENGTH_LONG)
                    .show()
                Log.e("post",t.localizedMessage.toString())
//                findViewById<TextView>(R.id.social_media_heading).text = t.localizedMessage.toString()
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
