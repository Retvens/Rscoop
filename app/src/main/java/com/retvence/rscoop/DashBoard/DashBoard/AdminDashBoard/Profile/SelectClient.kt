package com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DataCollections.OwnersData
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectClient : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var selectAdapter: SelectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_client)

        recyclerView = findViewById(R.id.select_recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)


        getClient()

    }

    private fun getClient() {

        val send = RetrofitBuilder.retrofitBuilder.getOwner("")

        send.enqueue(object : Callback<List<OwnersData>?> {
            override fun onResponse(
                call: Call<List<OwnersData>?>,
                response: Response<List<OwnersData>?>
            ) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    selectAdapter = SelectAdapter(this@SelectClient,response)
                    selectAdapter.notifyDataSetChanged()
                    recyclerView.adapter = selectAdapter
                }else{
                    Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<OwnersData>?>, t: Throwable) {
                Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
            }
        })
    }
}