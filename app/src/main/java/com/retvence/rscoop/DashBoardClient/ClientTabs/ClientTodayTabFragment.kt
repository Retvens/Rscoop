package com.retvence.rscoop.DashBoardClient.ClientTabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoardClient.ClientTodayTaskAdapter
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvence.rscoop.SharedStorage.SharedPreferenceManagerAdmin
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormatSymbols
import java.util.*

class ClientTodayTabFragment : Fragment() {

    private lateinit var owner_id: String

    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var clientTaskAdapter: ClientTodayTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_today_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        owner_id = SharedPreferenceManagerAdmin.getInstance(context!!).user.owner_id.toString()

        shimmerFrameLayout = view.findViewById(R.id.shimmer_tasks_to_container)

        //recycler
        recyclerView = view.findViewById(R.id.client_today_tab_tasks)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(-1)

        allTaskData()
    }

    private fun allTaskData() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val monthName = DateFormatSymbols().months[month]

        val currentDate = "$day"+" "+"$monthName"+" "+"$year"

        val getData = RetrofitBuilder.retrofitBuilder.getTodayTask(owner_id,currentDate)
        getData.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {

                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE

                val response = response.body()!!
                if (response != null && view != null) {
                    clientTaskAdapter = ClientTodayTaskAdapter(context!!, response)
                    clientTaskAdapter.notifyDataSetChanged()
                    recyclerView.adapter = clientTaskAdapter

                }
            }
            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {
                Toast.makeText(context,t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}