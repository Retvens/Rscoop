package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoardIgniter.TaskFragment.RecentAdapter
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CompletedTasks : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var CompleteAdapter: RecentRecycler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.Complete_task_admin)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        getData()

    }

    private fun getData() {
        val status = "Done"
        val send = RetrofitBuilder.retrofitBuilder.completeTask(status)

        send.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                val response = response.body()!!

                if (isAdded) {
                    CompleteAdapter = RecentRecycler(requireActivity(), response)
                    CompleteAdapter.notifyDataSetChanged()
                    recyclerView.adapter = CompleteAdapter

                    recyclerView.visibility = View.VISIBLE

                }
            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {

            }
        })
    }
}