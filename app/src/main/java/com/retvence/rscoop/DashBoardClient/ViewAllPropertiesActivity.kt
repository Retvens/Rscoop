package com.retvence.rscoop.DashBoardClient

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoardIgniter.RecentPropertiesDataClass
import com.retvence.rscoop.SharedStorage.SharedPreferenceManagerAdmin
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewAllPropertiesActivity : AppCompatActivity() {

    lateinit var clientAdapter: ClientAllPropertyAdapter

    lateinit var search : TextView

    private lateinit var owner_id : String

    lateinit var recyclerProperties: RecyclerView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_properties)

        owner_id = SharedPreferenceManagerAdmin.getInstance(this).user.owner_id.toString()

        findViewById<ImageView>(R.id.client_properties_back_btn).setOnClickListener {
            onBackPressed()
        }

        search = findViewById(R.id.client_search_property)

        recyclerProperties = findViewById(R.id.client_properties_recycler)
        shimmerFrameLayout = findViewById(R.id.client_shimmer_properties_container)
        recyclerProperties.setHasFixedSize(true)
        recyclerProperties.layoutManager = LinearLayoutManager(this)

        getClientHotelData()

    }

    private fun getClientHotelData() {
        val retrofit = RetrofitBuilder.retrofitBuilder.getClientHotel(owner_id)
        retrofit.enqueue(object : Callback<List<RecentPropertiesDataClass>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<RecentPropertiesDataClass>?>,
                response: Response<List<RecentPropertiesDataClass>?>
            ) {
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE

                if (response.isSuccessful){
                    val response = response.body()!!
                    clientAdapter = ClientAllPropertyAdapter(this@ViewAllPropertiesActivity,response)
                    clientAdapter.notifyDataSetChanged()
                    recyclerProperties.adapter = clientAdapter

                    recyclerProperties.visibility = View.VISIBLE

                    search.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                            val originalData = response.toList()
                            val filterData = originalData.filter { item ->
                                item.hotel_name.contains(p0.toString(),ignoreCase = true)
                            }

                            clientAdapter.updateData(filterData)
                        }

                        override fun afterTextChanged(p0: Editable?) {

                        }

                    })
                }
            }
            override fun onFailure(call: Call<List<RecentPropertiesDataClass>?>, t: Throwable) {
                Toast.makeText(this@ViewAllPropertiesActivity,t.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}