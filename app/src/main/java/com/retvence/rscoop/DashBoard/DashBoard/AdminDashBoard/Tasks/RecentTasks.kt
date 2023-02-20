package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvence.rscoop.DataCollections.TaskData
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.retvens.rscoop.R

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecentTasks : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recentrecycler: RecentRecycler

    private lateinit var shimmerFrameLayout : ShimmerFrameLayout
    private lateinit var shimmerLayout : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recent_recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        shimmerFrameLayout = view.findViewById(R.id.shimmer_recent_tasks_view_container)
        shimmerLayout = view.findViewById(R.id.shimmer_layout_recent_tasks)


        getData()

    }

    private fun getData() {

        val data = RetrofitBuilder.retrofitBuilder.getTask()

       data.enqueue(object : Callback<List<GetTaskData>?> {
           override fun onResponse(
               call: Call<List<GetTaskData>?>,
               response: Response<List<GetTaskData>?>
           ) {
               val response = response.body()!!

               if (isAdded) {
                   recentrecycler = RecentRecycler(requireActivity(), response)
                   recentrecycler.notifyDataSetChanged()
                   recyclerView.adapter = recentrecycler

                   recyclerView.visibility = View.VISIBLE
                   shimmerLayout.setVisibility(View.GONE)
               }
           }

           override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {

           }
       })

    }

}