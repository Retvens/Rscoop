package com.retvence.rscoop.DashBoardIgniter.TaskFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormatSymbols
import java.util.*

class TodayFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var todayAdapter: RecentAdapter
    lateinit var currentDate:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.Today_task)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)


        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val monthName = DateFormatSymbols().months[month]

        currentDate = String.format("%02d %s %04d", day, monthName, year)
        getData()
    }

    private fun getData() {
        val date = currentDate
        val send = RetrofitBuilder.retrofitBuilder.TodayTask(date)
        send.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                val response = response.body()!!

                if (isAdded) {
                    todayAdapter = RecentAdapter(requireActivity(), response)
                    todayAdapter.notifyDataSetChanged()
                    recyclerView.adapter = todayAdapter

                    recyclerView.visibility = View.VISIBLE

                }
            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {

            }
        })
    }
}

