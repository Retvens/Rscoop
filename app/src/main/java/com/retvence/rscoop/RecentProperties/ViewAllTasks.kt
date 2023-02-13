package com.retvens.rscoop.RecentProperties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rscoop.ApiRequests.RetrofitBuilder
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.example.rscoop.DataCollections.TaskData

import com.facebook.shimmer.ShimmerFrameLayout
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewAllTasks : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var recentrecycler: RecentRecycler

    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    lateinit var shimmerLayout: LinearLayout
    private lateinit var search:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_tasks)

        val backbtn = findViewById<ImageView>(R.id.tasks_back_btn)
        backbtn.setOnClickListener {
            startActivity(Intent(this, AdminDashBoard::class.java))
            finish()
        }

        recyclerView = findViewById(R.id.recycler_Tasks)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //search
        search = findViewById(R.id.search_tasks)

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

                search.addTextChangedListener(object :TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        val originalData = response.toList()
                        val filterData = originalData.filter { item ->
                            item.hotel_name.contains(p0.toString(),ignoreCase = true)
                        }

                        recentrecycler.updateData(filterData)
                    }

                    override fun afterTextChanged(p0: Editable?) {

                    }

                })

            }
            override fun onFailure(call: Call<List<TaskData>?>, t: Throwable) {
                Toast.makeText(this@ViewAllTasks,t.localizedMessage,Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}