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
import com.retvence.rscoop.DashBoardClient.ClientTaskAdapter
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientTodoFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var clientTaskAdapter: ClientTaskAdapter
    lateinit var shimmerFrameLayout: ShimmerFrameLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById(R.id.recycler_Tasks_todo_client)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        shimmerFrameLayout = view.findViewById(R.id.shimmer_tasks_container_todo_c)

        allTaskData()
    }

    private fun allTaskData() {
        val getData = RetrofitBuilder.retrofitBuilder.getTask()
        getData.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    clientTaskAdapter = ClientTaskAdapter(context!!,response)
                    clientTaskAdapter.notifyDataSetChanged()
                    recyclerView.adapter = clientTaskAdapter

                    shimmerFrameLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                }else{
                    Toast.makeText(context,response.code(), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {
                Toast.makeText(context,t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}