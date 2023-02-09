package com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rscoop.ApiRequests.RetrofitBuilder
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerAdminView
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.example.rscoop.DataCollections.OwnersData
import com.example.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecentTasks : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recentrecycler:RecentRecycler

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


        getData()

    }

    private fun getData() {

        val data = RetrofitBuilder.retrofitBuilder.getOwner()

        data.enqueue(object : Callback<List<OwnersData>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<OwnersData>?>,
                response: Response<List<OwnersData>?>
            ) {
                val response = response.body()!!

                if (response != null){


                    recentrecycler = RecentRecycler(requireActivity(),response)
                    recentrecycler.notifyDataSetChanged()
                    recyclerView.adapter = recentrecycler
                }

            }

            override fun onFailure(call: Call<List<OwnersData>?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

}