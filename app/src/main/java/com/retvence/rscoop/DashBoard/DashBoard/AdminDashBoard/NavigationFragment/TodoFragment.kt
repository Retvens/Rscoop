package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mackhartley.roundedprogressbar.RoundedProgressBar
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.FragmentAdapter
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TodayTasks
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.UpcomingTasks
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.CompletedTasks
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.RecentTasks
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TodoFragment : Fragment() {

    lateinit var tabLayout: TabLayout
    private lateinit var task:TextView
    private lateinit var pending:TextView
    private lateinit var complete:TextView
    var overallTask:Double = 0.0
    var completeTask:Double = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            tabLayout = view.findViewById<TabLayout>(R.id.tabs)

            val tab1 = tabLayout.newTab().setText("Recent")
            val tab2 = tabLayout.newTab().setText("Today")
            val tab3 = tabLayout.newTab().setText("Completed")
            tabLayout.addTab(tab1)
            tabLayout.addTab(tab2)
            tabLayout.addTab(tab3)

            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RecentTasks())
                .commit()

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val fragment = when (tab.position) {
                        0 -> RecentTasks()
                        1 -> TodayTasks()
                        2 -> CompletedTasks()
                        else -> RecentTasks()
                    }
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            // Set the initial tab selection
            tabLayout.getTabAt(0)?.select()

            pending = view.findViewById<TextView>(R.id.todo_text)
            task = view.findViewById<TextView>(R.id.total_task)
            complete = view.findViewById(R.id.todo_done_text)


            getTask()
            getPendingTask()
            getComplteTask()




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

                    completeTask = response.size.toDouble()

                }else{
                    Toast.makeText(context,response.code().toString(),Toast.LENGTH_LONG).show()
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
                    Toast.makeText(context,response.code().toString(),Toast.LENGTH_LONG).show()
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
                    task.setText("Have total ${response.size} Task")

                    overallTask = response.size.toDouble()


                    val send = RetrofitBuilder.retrofitBuilder.completeTask("Done")
                    send.enqueue(object : Callback<List<GetTaskData>?> {
                        override fun onResponse(
                            call: Call<List<GetTaskData>?>,
                            response: Response<List<GetTaskData>?>
                        ) {
                            if (response.isSuccessful){
                                val response1 = response.body()!!
                                val progressBar = view!!.findViewById<RoundedProgressBar>(R.id.progressBar)
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
                    Toast.makeText(context,response.code().toString(),Toast.LENGTH_LONG).show()
                }


            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {

            }
        })
    }
}