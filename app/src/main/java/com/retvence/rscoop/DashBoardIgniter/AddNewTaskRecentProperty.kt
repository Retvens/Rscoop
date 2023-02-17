package com.retvence.rscoop.DashBoardIgniter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewTaskRecentProperty : AppCompatActivity() {

    lateinit var recentAdapter : RecentPropertiesAdapterAT

    lateinit var recyclerProperties: RecyclerView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_task_recent_property)

        recyclerProperties = findViewById(R.id.recycler_properties_add_task)
        shimmerFrameLayout = findViewById(R.id.shimmer_properties_view_container_add_task)
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
                    response: Response<List<RecentPropertiesDataClass>?>
                ) {
                    val response = response.body()!!
                    recentAdapter = RecentPropertiesAdapterAT(this@AddNewTaskRecentProperty, response)
                    recentAdapter.notifyDataSetChanged()
                    recyclerProperties.adapter = recentAdapter

                    shimmerFrameLayout.setVisibility(View.GONE)
                    recyclerProperties.visibility = View.VISIBLE
                }

                override fun onFailure(call: Call<List<RecentPropertiesDataClass>?>, t: Throwable) {
                    Toast.makeText(baseContext,t.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            })

        }

}