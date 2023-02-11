package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rscoop.ApiRequests.RetrofitBuilder
import com.example.rscoop.DataCollections.HotelsData
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment.Adapters.BookmarkAdapter
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookmarkAdapter: BookmarkAdapter
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var searchBar:EditText
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

        searchBar = view.findViewById<EditText>(R.id.searchbox2)

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


                if (response != null && isAdded) {
                    val originalData = response.toList()

                    bookmarkAdapter = BookmarkAdapter(requireActivity(), response)
                    bookmarkAdapter.notifyDataSetChanged()
                    recyclerView.adapter = bookmarkAdapter

                    searchBar.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {

                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        }

                        override fun afterTextChanged(p0: Editable?) {
                            val filterData = originalData.filter { item ->
                                item.hotel_name.contains(p0.toString(), ignoreCase = true)
                            }

                            bookmarkAdapter.updateData(filterData)
                        }

                    })
                }


            }

            override fun onFailure(call: Call<List<HotelsData>?>, t: Throwable) {

            }
        })
    }

}


