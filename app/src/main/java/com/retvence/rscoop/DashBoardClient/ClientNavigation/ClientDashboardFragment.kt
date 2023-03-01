package com.retvence.rscoop.DashBoardClient.ClientNavigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.mackhartley.roundedprogressbar.RoundedProgressBar
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoardClient.ClientMiniPropertyAdapter
import com.retvence.rscoop.DashBoardClient.ClientTabs.ClientCompletedTabFragment
import com.retvence.rscoop.DashBoardClient.ClientTabs.ClientRecentTabFragment
import com.retvence.rscoop.DashBoardClient.ClientTabs.ClientTodayTabFragment
import com.retvence.rscoop.DashBoardClient.ViewAllPropertiesActivity
import com.retvence.rscoop.DashBoardClient.ViewAllTaskOfProperty
import com.retvence.rscoop.DashBoardIgniter.AddNewTaskRecentProperty
import com.retvence.rscoop.DashBoardIgniter.EgniterRecycler
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.CompletedFragment
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.RecentFragment
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.TodayFragment
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvence.rscoop.SharedStorage.SharedPreferenceManagerAdmin
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormatSymbols
import java.util.*

class ClientDashboardFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerViewC: RecyclerView
    private lateinit var hotelAdapter: ClientMiniPropertyAdapter
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    private lateinit var search: EditText
    private lateinit var tasks:TextView
    private lateinit var pending:TextView
    private lateinit var complete:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewAll = view.findViewById<TextView>(R.id.view_all_dash_client)
        viewAll.setOnClickListener {
            startActivity(Intent(context, ViewAllPropertiesActivity::class.java))
        }

        tasks = view.findViewById(R.id.total_task3)
        pending = view.findViewById(R.id.todo_text3)
        complete = view.findViewById(R.id.todo_done_text3)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val monthName = DateFormatSymbols().months[month]

        val currentDate = "$monthName"+" "+"$day"+" "+"$year"

        view.findViewById<TextView>(R.id.Month).text = currentDate

        search = view.findViewById(R.id.search_property_client_dashboard)

        shimmerFrameLayout = view.findViewById(R.id.client_shimmer_dash)
        recyclerViewC = view.findViewById(R.id.client_recycler_dash)
        recyclerViewC.setHasFixedSize(true)
        recyclerViewC.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)


        //All task TabLayout
        tabLayout = view.findViewById(R.id.client_tabs)

        val tab1 = tabLayout.newTab().setText("Recent")
        val tab2 = tabLayout.newTab().setText("Today")
        val tab3 = tabLayout.newTab().setText("Completed")
        tabLayout.addTab(tab1)
        tabLayout.addTab(tab2)
        tabLayout.addTab(tab3)

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_client_tabs_container, ClientRecentTabFragment())
            .commit()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment = when (tab.position) {
                    0 -> ClientRecentTabFragment()
                    1 -> ClientTodayTabFragment()
                    2 -> ClientCompletedTabFragment()
                    else -> ClientRecentTabFragment()
                }
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragment_client_tabs_container, fragment)
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
        val owner_id = SharedPreferenceManagerAdmin.getInstance(context!!).user.owner_id.toString()
        val data = RetrofitBuilder.retrofitBuilder.getHotel(owner_id)

        data.enqueue(object : Callback<List<HotelsData>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<HotelsData>?>,
                response: Response<List<HotelsData>?>
            ) {

                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE

                val response = response.body()!!

                if (response != null && view != null){

                    hotelAdapter = ClientMiniPropertyAdapter(context!!, response)
                    hotelAdapter.notifyDataSetChanged()
                    recyclerViewC.adapter = hotelAdapter
                    recyclerViewC.visibility = View.VISIBLE

                    search.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            val originalData = response.toList()
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
                Toast.makeText(requireContext(), t.localizedMessage!!.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun getComplteTask() {
        val owner_id = SharedPreferenceManagerAdmin.getInstance(context!!).user.owner_id.toString()
        val status = "Done"
        val data = RetrofitBuilder.retrofitBuilder.individualCompleteTask(owner_id,status)

        data.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    complete.setText("${response.size} Tasks")


                }else{
                    Toast.makeText(context,response.code().toString(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {

            }
        })
    }

    private fun getPendingTask() {
        val owner_id = SharedPreferenceManagerAdmin.getInstance(context!!).user.owner_id.toString()
        val status = "Pending"
        val data = RetrofitBuilder.retrofitBuilder.individualCompleteTask(owner_id,status)

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
        val owner_id = SharedPreferenceManagerAdmin.getInstance(context!!).user.owner_id.toString()
        val data = RetrofitBuilder.retrofitBuilder.getCTask(owner_id)
        data.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    tasks.setText("Have total ${response.size} Task")

                    val overallTask = response.size.toDouble()


                    val send = RetrofitBuilder.retrofitBuilder.individualCompleteTask(owner_id,"Done")
                    send.enqueue(object : Callback<List<GetTaskData>?> {
                        override fun onResponse(
                            call: Call<List<GetTaskData>?>,
                            response: Response<List<GetTaskData>?>
                        ) {
                            if (response.isSuccessful){
                                val response1 = response.body()!!
                                val progressBar = view!!.findViewById<RoundedProgressBar>(R.id.progressBarCD)
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