package com.example.rscoop.RecentProperties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rscoop.ApiRequests.RetrofitBuilder
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.example.rscoop.DataCollections.TaskData
import com.example.rscoop.R
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewAllTasks : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recentrecycler: RecentRecycler

    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    lateinit var shimmerLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_tasks)

        recyclerView = findViewById(R.id.recycler_Tasks)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        shimmerFrameLayout = findViewById(R.id.shimmer_tasks_view_container)
        shimmerLayout = findViewById(R.id.shimmer_layout_tasks)

        allTaskData()
    }


    private fun allTaskData() {
        val allTask = RetrofitBuilder.retrofitBuilder.getTask()
        allTask.enqueue(object : Callback<List<TaskData>?> {
            override fun onResponse(
                call: Call<List<TaskData>?>,
                response: Response<List<TaskData>?>
            ) {
                val response = response.body()!!
                recentrecycler = RecentRecycler(baseContext,response)
                recentrecycler.notifyDataSetChanged()
                recyclerView.adapter = recentrecycler

                shimmerLayout.setVisibility(View.GONE)
                recyclerView.visibility = View.VISIBLE
            }
            override fun onFailure(call: Call<List<TaskData>?>, t: Throwable) {
                Toast.makeText(this@ViewAllTasks,t.localizedMessage,Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}