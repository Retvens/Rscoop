package com.retvens.rscoop.ClientInformation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DataCollections.HotelsData
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

        val backbtn = findViewById<ImageView>(R.id.backarrow)
        backbtn.setOnClickListener {
            onBackPressed()
        }

        //defined items
        val client_Name = findViewById<TextView>(R.id.Client_Name)
        val hotelImage = findViewById<ImageView>(R.id.clienthotelimg)
        val whatsappClient = findViewById<ImageView>(R.id.whatsapp_client)
        val clientImage = findViewById<ImageView>(R.id.imageOfClient)
        val client_Name2 = findViewById<TextView>(R.id.Client_Name2)
        val number = findViewById<TextView>(R.id.client_number)
        val add = findViewById<TextView>(R.id.client_address)


        val name = intent.getStringExtra("client_name")
        val profile = intent.getStringExtra("client_image")
        val phone = intent.getStringExtra("client_phone")
        val mail = intent.getStringExtra("client_e")
        val address = intent.getStringExtra("client_address")
        val cover = intent.getStringExtra("client_cover")

        whatsappClient.setOnClickListener {
            val uriForWhat: Uri = Uri.parse("https://whatsapp.com")
            startActivity(Intent(Intent.ACTION_VIEW,uriForWhat))
        }

        findViewById<ImageView>(R.id.call).setOnClickListener {
            val dailIntent = Intent(Intent.ACTION_DIAL)
            dailIntent.data = Uri.parse("tel:" + phone.toString())
            startActivity(dailIntent)
        }
        findViewById<ImageView>(R.id.email).setOnClickListener {
            val uriMail: Uri = Uri.parse("mailto:"+ mail.toString())
            val intentMail = Intent(Intent.ACTION_SENDTO,uriMail)
            intentMail.putExtra(Intent.EXTRA_SUBJECT,"test")
            startActivity(intentMail)
        }

        client_Name2.text = name
        client_Name.text = name.toString()
        number.text = phone
        add.text = address
        Glide.with(baseContext).load(cover).into(hotelImage)
        Glide.with(baseContext).load(profile).into(clientImage)

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

            }
        })
    }
}