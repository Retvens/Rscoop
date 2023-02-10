package com.example.rscoop.RecentProperties

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rscoop.ApiRequests.RetrofitBuilder
import com.example.rscoop.DataCollections.OwnersData
import com.example.rscoop.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClientCountries : AppCompatActivity() {

    private lateinit var clientCountriesAdapter: ClientCountriesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmer: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_countries)

        //recycler define
        recyclerView = findViewById(R.id.recyclerOfClientCountry)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        shimmer = findViewById(R.id.client_shimmer_view)

        getClients()

    }

    private fun getClients() {

        val data = RetrofitBuilder.retrofitBuilder.getOwner()

        data.enqueue(object : Callback<List<OwnersData>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<OwnersData>?>,
                response: Response<List<OwnersData>?>
            ) {

                shimmer.stopShimmer()
                shimmer.visibility = View.GONE

                val searchBar = findViewById<EditText>(R.id.searchbox)
                val response = response.body()!!

                if (response != null){
                    val originalData = response.toList()
                    clientCountriesAdapter = ClientCountriesAdapter(this@ClientCountries,response)
                    clientCountriesAdapter.notifyDataSetChanged()
                    recyclerView.adapter = clientCountriesAdapter

                    searchBar.addTextChangedListener(object :TextWatcher{
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
                                item.Name.contains(p0.toString(),ignoreCase = true)
                            }

                            clientCountriesAdapter.updateData(filterData)
                        }

                    })


                }

                clientCountriesAdapter.setOnItemClickListener(object :ClientCountriesAdapter.onItemClickListener{
                    override fun onClick(position: Int) {

                        Toast.makeText(this@ClientCountries,"working on it",Toast.LENGTH_LONG).show()


                    }

                })

            }

            override fun onFailure(call: Call<List<OwnersData>?>, t: Throwable) {

            }
        })

    }
}