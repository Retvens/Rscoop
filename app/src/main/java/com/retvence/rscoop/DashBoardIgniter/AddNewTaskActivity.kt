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
        Toast.makeText(this@AddNewTaskActivity,"Clicked",Toast.LENGTH_SHORT)
            .show()
        val facebook = fbPost.text.toString()
        val Linkedin = linkedinPost.text.toString()
        val instagram = instaPost.text.toString()
        val twitter = twitterPost.text.toString()
        val Pinterest = pinterestPost.text.toString()
        val GMB = tripadPost.text.toString()
        val Google_review = googlePost.text.toString()

        val upload = RetrofitBuilder.retrofitBuilder.createSocialMeadia(TaskData("","New Hotel",
            "","20/10/30",facebook, Linkedin,instagram,twitter,Pinterest,GMB,Google_review,"data:image/jpeg;base64,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"))
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
