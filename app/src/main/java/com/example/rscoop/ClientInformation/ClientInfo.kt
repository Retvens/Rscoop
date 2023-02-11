package com.retvens.rscoop.ClientInformation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rscoop.ApiRequests.RetrofitBuilder
import com.example.rscoop.DataCollections.HotelsData
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientInfo : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClientAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_info)

        //defined items
        val client_Name = findViewById<TextView>(R.id.Client_Name)
        val hotelImage = findViewById<ImageView>(R.id.clienthotelimg)
        val clientImage = findViewById<ImageView>(R.id.imageOfClient)
        val client_Name2 = findViewById<TextView>(R.id.Client_Name2)
        val number = findViewById<TextView>(R.id.client_number)
        val add = findViewById<TextView>(R.id.client_address)


        val name = intent.getStringExtra("client_name")
        val image = intent.getStringExtra("client_image")
        val phone = intent.getStringExtra("client_phone")
        val address = intent.getStringExtra("client_address")

        client_Name2.text = name
        client_Name.text = name.toString()
        number.text = phone
        add.text = address
        Glide.with(baseContext).load(image).into(hotelImage)
        Glide.with(baseContext).load(image).into(clientImage)

        //recycler
        recyclerView  = findViewById(R.id.recyclerClientinfo)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL,false)


        getHotels()
    }

    private fun getHotels() {

        val owner = intent.getStringExtra("owner")
        val data = RetrofitBuilder.retrofitBuilder.getHotel(owner.toString())
        data.enqueue(object : Callback<List<HotelsData>?> {
            override fun onResponse(
                call: Call<List<HotelsData>?>,
                response: Response<List<HotelsData>?>
            ) {
                val response = response.body()!!

                adapter = ClientAdapter(baseContext, response)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<List<HotelsData>?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}