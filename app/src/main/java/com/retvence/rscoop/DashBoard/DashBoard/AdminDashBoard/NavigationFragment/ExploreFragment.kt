package com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

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
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.FragmentAdapter
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TodayTasks
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.UpcomingTasks
import com.retvence.rscoop.DataCollections.CountryData
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerAdminView
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerHotelsView
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.CompletedTasks
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.RecentTasks
import com.retvens.rscoop.R
import com.retvens.rscoop.RecentProperties.RecentPropertiesView
import com.retvens.rscoop.RecentProperties.ViewAllTasks
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreFragment() : Fragment(){

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var recyclerView: RecyclerView
    private lateinit var hotelRecyclerView:RecyclerView
    private lateinit var adapter: RecyclerAdminView
    private lateinit var hotelAdapter: RecyclerHotelsView
    private lateinit var tabLayout:TabLayout
    private  var recentTasks = RecentTasks()
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var searchBar:EditText


    private lateinit var recentProperties: TextView
    private lateinit var allTasks: TextView

    private lateinit var exploreProfile: ImageView


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

        exploreProfile = view.findViewById(R.id.exploreProfile)
        exploreProfile.setOnClickListener {
           val profileFragment = ProfileFragment()
            val transection: FragmentTransaction = fragmentManager!!.beginTransaction()
            transection.replace(R.id.fragment_container,profileFragment)
                transection.commit()
        }

        allTasks = view.findViewById(R.id.view_all_tasks)
        allTasks.setOnClickListener {
            startActivity(Intent(context, ViewAllTasks::class.java))
        }
        recentProperties = view.findViewById(R.id.view_properties)
        recentProperties.setOnClickListener {
            startActivity(Intent(context, RecentPropertiesView::class.java))
        }


// All Tasks Tab Layout
        tabLayout = view.findViewById(R.id.tabLayout)

        val tab1 = tabLayout.newTab().setText("Recent")
        val tab2 = tabLayout.newTab().setText("Today")
        val tab3 = tabLayout.newTab().setText("Completed")
        tabLayout.addTab(tab1)
        tabLayout.addTab(tab2)
        tabLayout.addTab(tab3)

        childFragmentManager.beginTransaction()
            .replace(R.id.container, RecentTasks())
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
                    .replace(R.id.container, fragment)
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Set the initial tab selection
        tabLayout.getTabAt(0)?.select()

        getCountry()
        getHotels()


        searchBar = view.findViewById<EditText>(R.id.search_Bar)

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

               if (response != null && view != null){
                   val originalData = response.toList()


                   adapter = RecyclerAdminView(requireActivity(),response)
                   adapter.notifyDataSetChanged()
                   recyclerView.adapter = adapter

                   searchBar.addTextChangedListener(object : TextWatcher{
                       override fun beforeTextChanged(
                           p0: CharSequence?,
                           p1: Int,
                           p2: Int,
                           p3: Int
                       ) {

                       }

                       override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                           val filterData = originalData.filter { item ->
                               item.Name.contains(p0.toString(),ignoreCase = true)
                           }

                           adapter.updateData(filterData)
                       }

                       override fun afterTextChanged(p0: Editable?) {

                       }

                   })



               }

           }

           override fun onFailure(call: Call<List<CountryData>?>, t: Throwable) {

           }
       })


    }


    //Fetching the Data of Hotels
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

              if (response != null && isAdded){

                  val originalData = response.toList()

                  hotelAdapter = RecyclerHotelsView(context!!, response!!)
                  hotelAdapter.notifyDataSetChanged()
                  hotelRecyclerView.adapter = hotelAdapter

                  //searchBar
                  searchBar.addTextChangedListener(object : TextWatcher{
                      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

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


    private fun replaceFragment(fragment:Fragment) {
        if (fragment !=null){
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }


}
