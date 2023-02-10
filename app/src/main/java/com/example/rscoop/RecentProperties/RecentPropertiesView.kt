package com.example.rscoop.RecentProperties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rscoop.ApiRequests.RetrofitBuilder
import com.example.rscoop.ApiRequests.RetvensUrls
import com.example.rscoop.DataCollections.HotelsData
import com.example.rscoop.R
import com.facebook.shimmer.ShimmerFrameLayout
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

        recyclerProperties = findViewById(R.id.recycler_properties)
        shimmerFrameLayout = findViewById(R.id.shimmer_properties_view_container)
        shimmerLayout = findViewById(R.id.shimmer_layout)

        recyclerProperties.setHasFixedSize(true)
        recyclerProperties.layoutManager = LinearLayoutManager(this)

        recyclerViewData()
    }

    private fun recyclerViewData() {
        recyclerProperties.setVisibility(View.GONE)

        val retrofit = RetrofitBuilder.retrofitBuilder.getHotel()

        retrofit.enqueue(object : Callback<List<HotelsData>?> {
            override fun onResponse(
                call: Call<List<HotelsData>?>,
                response: Response<List<HotelsData>?>
            ) {
                val response = response.body()!!
                adapter = RecentPropertiesAdapter(baseContext, response)
                adapter.notifyDataSetChanged()
                recyclerProperties.adapter =adapter

                shimmerLayout.setVisibility(View.GONE)
                recyclerProperties.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<List<HotelsData>?>, t: Throwable) {
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