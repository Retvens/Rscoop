package com.retvence.rscoop.DashBoardIgniter

import android.annotation.SuppressLint
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
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TodayTasks
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.CompletedFragment
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.RecentFragment
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.TodayFragment
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvence.rscoop.SharedStorage.SharedPreferenceManagerAdmin
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerHotelsView
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.CompletedTasks
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.RecentTasks
import com.retvens.rscoop.MainActivity
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormatSymbols
import java.util.*

class IgniterDashBoard : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var hotelAdapter:EgniterRecycler
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var searchBar:EditText
    private lateinit var logOut:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_igniter_dash_board)

        val month = findViewById<TextView>(R.id.Month)
        val day = findViewById<TextView>(R.id.current_Date)
        val year = findViewById<TextView>(R.id.year)

        val calendar = Calendar.getInstance()
        val Year = calendar.get(Calendar.YEAR)
        val Month = calendar.get(Calendar.MONTH)
        val Day = calendar.get(Calendar.DAY_OF_MONTH)
        val monthName = DateFormatSymbols().months[Month]

        month.text = monthName
        day.text = Day.toString()
        year.text = Year.toString()

        searchBar = findViewById(R.id.Egniter_search)

        logOut = findViewById(R.id.logout_igniter)
        logOut.setOnClickListener {
            SharedPreferenceManagerAdmin.getInstance(this).clear()
            val intent = Intent(this@IgniterDashBoard, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(this@IgniterDashBoard,"Logged Out", Toast.LENGTH_SHORT).show()
        }

        shimmer = findViewById(R.id.Egniter_shimmer)
        val text = findViewById<TextView>(R.id.latest)
        text.setOnClickListener {
            startActivity(Intent(this,AddNewTaskRecentProperty::class.java))
        }

        //recyclerview

        val add = findViewById<ImageView>(R.id.addTask)
        add.setOnClickListener {
            startActivity(Intent(this,AddNewTaskActivity::class.java))
        }

        recyclerView = findViewById(R.id.Egniter_recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(baseContext,LinearLayoutManager.HORIZONTAL,false)

        //All task TabLayout
        tabLayout = findViewById(R.id.Egniter_tab)

        val tab1 = tabLayout.newTab().setText("Recent")
        val tab2 = tabLayout.newTab().setText("Today")
        val tab3 = tabLayout.newTab().setText("Completed")
        tabLayout.addTab(tab1)
        tabLayout.addTab(tab2)
        tabLayout.addTab(tab3)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container1, RecentFragment())
            .commit()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment = when (tab.position) {
                    0 -> RecentFragment()
                    1 -> TodayFragment()
                    2 -> CompletedFragment()
                    else -> RecentFragment()
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container1, fragment)
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Set the initial tab selection
        tabLayout.getTabAt(0)?.select()


        getHotels()

    }

    private fun getHotels() {
        val data = RetrofitBuilder.retrofitBuilder.getHotel("")

        data.enqueue(object : Callback<List<HotelsData>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<HotelsData>?>,
                response: Response<List<HotelsData>?>
            ) {
                shimmer.stopShimmer()
                shimmer.visibility = View.GONE

                val response = response.body()
                if (response != null ){
                    val originalData = response.toList()

                    hotelAdapter = EgniterRecycler(baseContext!!, response!!)
                    hotelAdapter.notifyDataSetChanged()
                    recyclerView.adapter = hotelAdapter

                    searchBar.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {

                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            val filterData = originalData.filter { item ->
                                item.hotel_name.contains(p0.toString(),ignoreCase = true)
                            }

                            hotelAdapter.updateData(filterData)

                        }

                        override fun afterTextChanged(p0: Editable?) {

                        }
                    })

                }

            }
            override fun onFailure(call: Call<List<HotelsData>?>, t: Throwable) {
//                Toast.makeText(requireContext(),t.message,Toast.LENGTH_LONG).show()
                Log.e("error",t.message.toString())
            }
        })
    }

}