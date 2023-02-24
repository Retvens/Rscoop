package com.retvence.rscoop.DashBoardClient.ClientNavigation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoardClient.ProfilePropertyAdapter
import com.retvence.rscoop.DashBoardIgniter.SelectPropertyAdapter
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvence.rscoop.SharedStorage.SharedPreferenceManagerAdmin
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.retvens.rscoop.MainActivity
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientProfileFragment : Fragment() {

    lateinit var profilePropertyAdapter: ProfilePropertyAdapter
    lateinit var recycler: RecyclerView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    lateinit var whatsapp : ImageView
    lateinit var call : ImageView
    lateinit var mail : ImageView

    private lateinit var logOut: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logOut = view.findViewById(R.id.logOut_client)
        logOut.setOnClickListener {
            context?.let { it1 -> SharedPreferenceManagerAdmin.getInstance(it1).clear() }
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(context,"Logged Out",Toast.LENGTH_SHORT).show()
        }


        whatsapp = view.findViewById(R.id.whatsapp_client_profile)
        call = view.findViewById(R.id.PhoneCall_client_profile)
        mail = view.findViewById(R.id.mali_client_profile)

        whatsapp.setOnClickListener {
        val uri : Uri = Uri.parse("http://whatsapp.com")
        val intent = Intent(Intent.ACTION_VIEW,uri)
        startActivity(intent)
        }
        call.setOnClickListener {
            val uriCall : Uri = Uri.parse("tel:"+"7905845935")
            val intentCall = Intent(Intent.ACTION_DIAL,uriCall)
            startActivity(intentCall)
        }
        mail.setOnClickListener {
            val uriMail:Uri = Uri.parse("mailto:"+"arjungupta0817@gmail.com")
            val intentMail = Intent(Intent.ACTION_SENDTO,uriMail)
            intentMail.putExtra(Intent.EXTRA_SUBJECT,"test")
            startActivity(intentMail)
        }

        shimmerFrameLayout = view.findViewById(R.id.recycler_shimmer_profile)
        recycler = view.findViewById(R.id.recycler_profile)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        getHotelData()
    }
    private fun getHotelData() {
        val retrofit = RetrofitBuilder.retrofitBuilder.getHotel("")
        retrofit.enqueue(object : Callback<List<HotelsData>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<HotelsData>?>,
                response: Response<List<HotelsData>?>
            ) {
                val response = response.body()!!
                profilePropertyAdapter = ProfilePropertyAdapter(context!!,response)
                profilePropertyAdapter.notifyDataSetChanged()
                recycler.adapter = profilePropertyAdapter

                recycler.visibility = View.VISIBLE
                shimmerFrameLayout.visibility = View.GONE
            }
            override fun onFailure(call: Call<List<HotelsData>?>, t: Throwable) {
                Toast.makeText(context,t.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}