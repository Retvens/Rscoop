package com.retvence.rscoop.DashBoardClient.ClientNavigation

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
import com.retvence.rscoop.DashBoardClient.ClientFavouriteTaskAdapter
import com.retvence.rscoop.DashBoardClient.ClientTaskAdapter
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvence.rscoop.SharedStorage.SharedPreferenceManagerAdmin
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientFavoriteFragment : Fragment() {

    private lateinit var owner_id : String

    private lateinit var recyclerView: RecyclerView
    private lateinit var clientTaskAdapter: ClientFavouriteTaskAdapter
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        owner_id = SharedPreferenceManagerAdmin.getInstance(context!!).user.owner_id.toString()


        recyclerView = view.findViewById(R.id.recycler_Tasks_favorite)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        shimmerFrameLayout = view.findViewById(R.id.shimmer_tasks_view_container_favorite)

        allTaskData()
    }

    private fun allTaskData() {
        val getData = RetrofitBuilder.retrofitBuilder.getCFavouriteTask(owner_id)
        getData.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE

                val response = response.body()!!
                if (view != null){
                    clientTaskAdapter = ClientFavouriteTaskAdapter(context!!,response)
                    clientTaskAdapter.notifyDataSetChanged()
                    recyclerView.adapter = clientTaskAdapter

                    recyclerView.visibility = View.VISIBLE

                }
            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {
                Toast.makeText(context,t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}