package com.retvence.rscoop.DashBoardIgniter

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TodayTasks
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerHotelsView
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.CompletedTasks
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.RecentTasks
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IgniterDashBoard : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var hotelAdapter:EgniterRecycler
    private lateinit var shimmer: ShimmerFrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_igniter_dash_board)

        shimmer = findViewById(R.id.Egniter_shimmer)

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
            .replace(R.id.container1, RecentTasks())
            .commit()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment = when (tab.position) {
                    0 -> RecentTasks()
                    1 -> TodayTasks()
                    2 -> CompletedTasks()
                    else -> RecentTasks()
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
                    hotelAdapter = EgniterRecycler(baseContext!!, response!!)
                    hotelAdapter.notifyDataSetChanged()
                    recyclerView.adapter = hotelAdapter
                }

            }
            override fun onFailure(call: Call<List<HotelsData>?>, t: Throwable) {
//                Toast.makeText(requireContext(),t.message,Toast.LENGTH_LONG).show()
                Log.e("error",t.message.toString())
            }
        })
    }

}