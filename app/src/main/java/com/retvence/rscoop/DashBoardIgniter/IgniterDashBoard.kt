package com.retvence.rscoop.DashBoardIgniter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.mackhartley.roundedprogressbar.RoundedProgressBar
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TodayTasks
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.CompletedFragment
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.RecentFragment
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.TodayFragment
import com.retvence.rscoop.DataCollections.GetTaskData
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
    private lateinit var tasks:TextView
    private lateinit var pending:TextView
    private lateinit var complete:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_igniter_dash_board)

        val month = findViewById<TextView>(R.id.Month)
        val day = findViewById<TextView>(R.id.current_Date)
        val year = findViewById<TextView>(R.id.year)
        tasks = findViewById(R.id.igniter_task)
        pending = findViewById(R.id.todo_text1)
        complete = findViewById(R.id.todo_done_text1)

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
           showCustomDialogBox()
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
        getTask()
        getPendingTask()
        getComplteTask()



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

    private fun getComplteTask() {
        val status = "Done"
        val data = RetrofitBuilder.retrofitBuilder.completeTask(status)

        data.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    complete.setText("${response.size} Tasks")

                }else{
                    Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {

            }
        })
    }

    private fun getPendingTask() {
        val status = "Pending"
        val data = RetrofitBuilder.retrofitBuilder.completeTask(status)

        data.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    pending.setText("${response.size} Tasks")



                }else{
                    Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {
            }
        })
    }

    private fun getTask() {
        val data = RetrofitBuilder.retrofitBuilder.getTask()
        data.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    tasks.setText("${response.size} Task")

                    val overallTask = response.size.toDouble()


                    val send = RetrofitBuilder.retrofitBuilder.completeTask("Done")
                    send.enqueue(object : Callback<List<GetTaskData>?> {
                        override fun onResponse(
                            call: Call<List<GetTaskData>?>,
                            response: Response<List<GetTaskData>?>
                        ) {
                            if (response.isSuccessful){
                                val response1 = response.body()!!
                                val progressBar = findViewById<RoundedProgressBar>(R.id.progressBar)
                                val totalTasks = overallTask
                                val completedTasks = response1.size.toDouble()
                                val pendingTasks = totalTasks - completedTasks
                                val pendingPercentage = (pendingTasks.toDouble() / totalTasks.toDouble()) * 100.0
                                progressBar.setProgressPercentage(pendingPercentage)

                            }
                        }

                        override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {

                        }
                    })


                }else{
                    Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_LONG).show()
                }


            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {

            }
        })
    }

    private fun showCustomDialogBox() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialoge)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)

        btnYes.setOnClickListener {
            SharedPreferenceManagerAdmin.getInstance(this).clear()
            val intent = Intent(this@IgniterDashBoard, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(this@IgniterDashBoard,"Logged Out", Toast.LENGTH_SHORT).show()
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}