package com.retvens.rscoop.RecentProperties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DashBoardIgniter.RecentPropertiesAdapterAT
import com.retvence.rscoop.DashBoardIgniter.RecentPropertiesDataClass
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecentPropertiesView : AppCompatActivity() {

    lateinit var adapter : RecentPropertiesAdapter
    lateinit var recyclerProperties: RecyclerView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    lateinit var shimmerLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_properties_view)

        val backbtn = findViewById<ImageView>(R.id.properties_back_btn)
        backbtn.setOnClickListener {
            startActivity(Intent(this, AdminDashBoard::class.java))
            finish()
        }

        recyclerProperties = findViewById(R.id.recycler_properties)
        shimmerFrameLayout = findViewById(R.id.shimmer_properties_view_container)
        shimmerLayout = findViewById(R.id.shimmer_layout)

        recyclerProperties.setHasFixedSize(true)
        recyclerProperties.layoutManager = LinearLayoutManager(this)

        recyclerViewData()
    }

    private fun recyclerViewData() {
        recyclerProperties.setVisibility(View.GONE)

        val retrofit = RetrofitBuilder.retrofitBuilder.getRecentProperty()

        retrofit.enqueue(object : Callback<List<RecentPropertiesDataClass>?> {
            override fun onResponse(
                call: Call<List<RecentPropertiesDataClass>?>,
                response: Response<List<RecentPropertiesDataClass>?>) {
                if (response.isSuccessful) {
                    val response = response.body()!!
                    adapter = RecentPropertiesAdapter(this@RecentPropertiesView, response)
                    adapter.notifyDataSetChanged()
                    recyclerProperties.adapter = adapter

                    shimmerLayout.setVisibility(View.GONE)
                    recyclerProperties.visibility = View.VISIBLE
                }else{
                    Log.d("res",response.errorBody().toString())
                    Toast.makeText(this@RecentPropertiesView,response.errorBody().toString(),Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<RecentPropertiesDataClass>?>, t: Throwable) {
               Toast.makeText(baseContext,t.localizedMessage,Toast.LENGTH_LONG)
                   .show()
            }
        })

    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmer()
        super.onPause()
    }

    override fun onResume() {
        shimmerFrameLayout.startShimmer()
        super.onResume()
    }
}