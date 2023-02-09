package com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rscoop.ApiRequests.RetrofitBuilder
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.Adapters.BookmarkAdapter
import com.example.rscoop.DashBoard.DashBoard.AdminDashBoard.RecyclerAdminView
import com.example.rscoop.DataCollections.HotelsData
import com.example.rscoop.R
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookmarkAdapter: BookmarkAdapter
    private lateinit var shimmer: ShimmerFrameLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView  = view.findViewById(R.id.bookmark_recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        shimmer = view.findViewById(R.id.bookmark_shimmer)

        getData()

    }

    private fun getData() {
        val data = RetrofitBuilder.retrofitBuilder.getHotel()

        data.enqueue(object : Callback<List<HotelsData>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<HotelsData>?>,
                response: Response<List<HotelsData>?>
            ) {

                shimmer.stopShimmer()
                shimmer.visibility = View.GONE

                val response = response.body()!!

                bookmarkAdapter = BookmarkAdapter(requireActivity(),response)
                bookmarkAdapter.notifyDataSetChanged()
                recyclerView.adapter = bookmarkAdapter

                bookmarkAdapter.setOnItemClickListener(object : BookmarkAdapter.onItemClickListener{
                    override fun onClick(position: Int) {
                        Toast.makeText(context,"work in progress",Toast.LENGTH_LONG).show()
                    }

                })
            }

            override fun onFailure(call: Call<List<HotelsData>?>, t: Throwable) {

            }
        })
    }

}