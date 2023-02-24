package com.retvence.rscoop.DashBoardClient.ClientTabs

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoardClient.ClientTaskAdapter
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.RecentAdapter
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientRecentTabFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var clientTaskAdapter:ClientTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_recent_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //recycler
        recyclerView = view.findViewById(R.id.client_recent_tab_tasks)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        allTaskData()
    }

    private fun allTaskData() {
        val getData = RetrofitBuilder.retrofitBuilder.getTask()
        getData.enqueue(object : Callback<List<GetTaskData>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {

                    val response = response.body()!!
                    clientTaskAdapter = ClientTaskAdapter(context!!,response)
                    clientTaskAdapter.notifyDataSetChanged()
                    recyclerView.adapter = clientTaskAdapter

            }
            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {
                Toast.makeText(context,t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}