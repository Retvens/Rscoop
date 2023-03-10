package com.retvens.rscoop.RecentProperties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DataCollections.OwnersData
import com.retvens.rscoop.R
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

        val backbtn = findViewById<ImageView>(R.id.Client_backbtn)
        backbtn.setOnClickListener {
            onBackPressed()
        }

        //recycler define
        recyclerView = findViewById(R.id.recyclerOfClientCountry)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        shimmer = findViewById(R.id.client_shimmer_view)

        getClients()

    }

    private fun getClients() {


        val country = intent.getStringExtra("country")
        val data = RetrofitBuilder.retrofitBuilder.getOwner(country.toString())

       data.enqueue(object : Callback<List<OwnersData>?> {
           override fun onResponse(
               call: Call<List<OwnersData>?>,
               response: Response<List<OwnersData>?>
           ) {
               shimmer.stopShimmer()
               shimmer.visibility = View.GONE

               val searchBar = findViewById<EditText>(R.id.searchbox)

               if (response.code() == 200){


                   val response = response.body()!!
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
           }

           override fun onFailure(call: Call<List<OwnersData>?>, t: Throwable) {
               Log.e("error",t.message.toString())
           }
       })

    }
}