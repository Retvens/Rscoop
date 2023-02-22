package com.retvence.rscoop.DashBoardClient.ClientNavigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoardClient.ClientTabs.ClientCompletedTabFragment
import com.retvence.rscoop.DashBoardClient.ClientTabs.ClientRecentTabFragment
import com.retvence.rscoop.DashBoardClient.ClientTabs.ClientTodayTabFragment
import com.retvence.rscoop.DashBoardClient.ViewAllTaskOfProperty
import com.retvence.rscoop.DashBoardIgniter.AddNewTaskRecentProperty
import com.retvence.rscoop.DashBoardIgniter.EgniterRecycler
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.CompletedFragment
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.RecentFragment
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.TodayFragment
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientDashboardFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerViewC: RecyclerView
    private lateinit var hotelAdapter: EgniterRecycler
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

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
            startActivity(Intent(context, ViewAllTaskOfProperty::class.java))
        }


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
    }

    private fun getHotels() {
        val data = RetrofitBuilder.retrofitBuilder.getHotel("")

        data.enqueue(object : Callback<List<HotelsData>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<HotelsData>?>,
                response: Response<List<HotelsData>?>
            ) {

                if (response.isSuccessful){
                    val response = response.body()!!
                    hotelAdapter = EgniterRecycler(context!!, response)
                    hotelAdapter.notifyDataSetChanged()
//                    recyclerViewC.adapter = hotelAdapter

                }
            }
            override fun onFailure(call: Call<List<HotelsData>?>, t: Throwable) {
                Toast.makeText(requireContext(),t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}