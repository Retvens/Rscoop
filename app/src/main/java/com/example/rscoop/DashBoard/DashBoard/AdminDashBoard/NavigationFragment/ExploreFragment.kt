package com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.rscoop.ApiRequests.RetrofitBuilder
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerAdminView
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerHotelsView
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.*
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.FragmentAdapter
import com.example.rscoop.DataCollections.CountryData
import com.example.rscoop.DataCollections.HotelsData
import com.example.rscoop.DataCollections.OwnersData
import com.example.rscoop.R
import com.example.rscoop.RecentProperties.ClientCountries
import com.example.rscoop.RecentProperties.RecentPropertiesView
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreFragment() : Fragment(){

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var recyclerView: RecyclerView
    private lateinit var hotelRecyclerView:RecyclerView
    private lateinit var adapter: RecyclerAdminView
    private lateinit var hotelAdapter: RecyclerHotelsView
    private lateinit var tabLabLayout:TabLayout
    private  var recentTasks = RecentTasks()
    private lateinit var shimmer: ShimmerFrameLayout

    private lateinit var recentProperties: TextView

    private  val recentTask =  RecentTasks()
    private val todayTasks = TodayTasks()
    private val upcomingTasks = UpcomingTasks()
    private val completedTasks = CompletedTasks()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //shimmer
        shimmer = view.findViewById(R.id.shimmer_view)

        //recycler for owner
        recyclerView  = view.findViewById(R.id.countryRecycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        //recycler for hotels
        hotelRecyclerView = view.findViewById(R.id.hotelsRecycler)
        hotelRecyclerView.setHasFixedSize(true)
        hotelRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        recentProperties = view.findViewById(R.id.view_properties)
        recentProperties.setOnClickListener {
            startActivity(Intent(context,RecentPropertiesView::class.java))
        }

// All Tasks Tab Layout
        var viewPager = view.findViewById<ViewPager>(R.id.viewPager1)
        tabLabLayout = view.findViewById<TabLayout>(R.id.tabLayout)


       val viewAdapter = FragmentAdapter(requireActivity().supportFragmentManager)

        viewAdapter.addFragment(RecentTasks(),"Recent")
        viewAdapter.addFragment(TodayTasks(),"Today")
        viewAdapter.addFragment(UpcomingTasks(),"Upcoming")
        viewAdapter.addFragment(CompletedTasks(),"Completed")

        viewPager.adapter = viewAdapter
        tabLabLayout.setupWithViewPager(viewPager)


        getCountry()
        getHotels()




    }

//    Fetching the Data of Country
    private fun getCountry() {
        val data = RetrofitBuilder.retrofitBuilder.getCountry()

       data.enqueue(object : Callback<List<CountryData>?> {
           override fun onResponse(
               call: Call<List<CountryData>?>,
               response: Response<List<CountryData>?>
           ) {
               shimmer.stopShimmer()
               shimmer.visibility = View.GONE

               val response = response.body()!!

               adapter = RecyclerAdminView(requireActivity(),response)
               adapter.notifyDataSetChanged()
               recyclerView.adapter = adapter

               adapter.setOnItemClickListener(object : RecyclerAdminView.onItemClickListener{
                   override fun onClick(position: Int) {
                       startActivity(Intent(context,ClientCountries::class.java))
                   }

               })
           }

           override fun onFailure(call: Call<List<CountryData>?>, t: Throwable) {

           }
       })


    }


    //Fetching the Data of Hotels
    private fun getHotels() {
        val data = RetrofitBuilder.retrofitBuilder.getHotel()

      data.enqueue(object : Callback<List<HotelsData>?> {
          @SuppressLint("NotifyDataSetChanged")
          override fun onResponse(
              call: Call<List<HotelsData>?>,
              response: Response<List<HotelsData>?>
          ) {
              shimmer.stopShimmer()
              shimmer.visibility = View.GONE

              val response = response.body()
              hotelAdapter = RecyclerHotelsView(requireActivity(), response!!)
              hotelAdapter.notifyDataSetChanged()
              hotelRecyclerView.adapter = hotelAdapter
          }

          override fun onFailure(call: Call<List<HotelsData>?>, t: Throwable) {

          }
      })
    }


    private fun replaceFragment(fragment:Fragment) {
        if (fragment !=null){
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }



}
