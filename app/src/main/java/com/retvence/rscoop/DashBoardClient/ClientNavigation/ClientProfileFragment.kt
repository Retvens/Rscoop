package com.retvence.rscoop.DashBoardClient.ClientNavigation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoardClient.ClientProfileData
import com.retvence.rscoop.DashBoardClient.ProfilePropertyAdapter
import com.retvence.rscoop.DashBoardIgniter.RecentPropertiesDataClass
import com.retvence.rscoop.DashBoardIgniter.SelectPropertyAdapter
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvence.rscoop.DataCollections.OwnersData
import com.retvence.rscoop.SharedStorage.SharedPreferenceManagerAdmin
import com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Tasks.TasksAdapter.RecentRecycler
import com.retvens.rscoop.MainActivity
import com.retvens.rscoop.R
import okhttp3.internal.http.promisesBody
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

    private lateinit var owner_id : String
    private lateinit var phone : String
    private lateinit var mailid : String

    private lateinit var name : TextView
    private lateinit var nameHotel : TextView
    private lateinit var number : TextView
    private lateinit var country : TextView

    private lateinit var cover : ImageView
    private lateinit var profile : ImageView

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

        owner_id = SharedPreferenceManagerAdmin.getInstance(context!!).user.owner_id.toString()

        logOut = view.findViewById(R.id.logOut_client)
        logOut.setOnClickListener {
            context?.let { it1 -> SharedPreferenceManagerAdmin.getInstance(it1).clear() }
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(context,"Logged Out",Toast.LENGTH_SHORT).show()
        }

        name = view.findViewById(R.id.client_Hotel_Name_profile)
        nameHotel = view.findViewById(R.id.Client_Hotel_Name2_profile)
        number = view.findViewById(R.id.client_hotel_contact_profile)
        country = view.findViewById(R.id.Client_hotel_country_profile)

        cover = view.findViewById(R.id.client_hotel_cover_profile)
        profile = view.findViewById(R.id.client_hotel_profile)


        whatsapp = view.findViewById(R.id.whatsapp_client_profile)
        call = view.findViewById(R.id.PhoneCall_client_profile)
        mail = view.findViewById(R.id.mali_client_profile)

        whatsapp.setOnClickListener {
        val uri : Uri = Uri.parse("http://whatsapp.com")
        val intent = Intent(Intent.ACTION_VIEW,uri)
        startActivity(intent)
        }
        call.setOnClickListener {
            val uriCall : Uri = Uri.parse("tel:"+ phone)
            val intentCall = Intent(Intent.ACTION_DIAL,uriCall)
            startActivity(intentCall)
        }
        mail.setOnClickListener {
            val uriMail:Uri = Uri.parse("mailto:"+ mailid)
            val intentMail = Intent(Intent.ACTION_SENDTO,uriMail)
            intentMail.putExtra(Intent.EXTRA_SUBJECT,"test")
            startActivity(intentMail)
        }

        shimmerFrameLayout = view.findViewById(R.id.recycler_shimmer_profile)
        recycler = view.findViewById(R.id.recycler_profile)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        getClientData()

        getHotelData()
    }

    private fun getClientData() {

        val client = RetrofitBuilder.retrofitBuilder.getClient(owner_id)

        client.enqueue(object : Callback<ClientProfileData?> {
            override fun onResponse(
                call: Call<ClientProfileData?>,
                response: Response<ClientProfileData?>
            ) {

                if (response.isSuccessful && view != null){
                val response = response.body()!!


                    val data  = response
                    name.text = data.Name
                    nameHotel.text = data.Name
                    number.text = data.Phone.toString()
                    country.text = data.Country
                    Glide.with(context!!).load(data.Cover_photo).into(cover)
                    Glide.with(context!!).load(data.Profile_photo).into(profile)

                }
            }

            override fun onFailure(call: Call<ClientProfileData?>, t: Throwable) {
                Toast.makeText(context,t.localizedMessage, Toast.LENGTH_LONG)
                    .show()
                Log.v("",t.message.toString())
            }
        })
    }
    private fun getHotelData() {
        val retrofit = RetrofitBuilder.retrofitBuilder.getClientHotel(owner_id)
        retrofit.enqueue(object : Callback<List<RecentPropertiesDataClass>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<RecentPropertiesDataClass>?>,
                response: Response<List<RecentPropertiesDataClass>?>
            ) {
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE

                val response = response.body()!!

                if (response != null && view != null){

                profilePropertyAdapter = ProfilePropertyAdapter(context!!,response)
                profilePropertyAdapter.notifyDataSetChanged()
                recycler.adapter = profilePropertyAdapter

                recycler.visibility = View.VISIBLE

                }
            }
            override fun onFailure(call: Call<List<RecentPropertiesDataClass>?>, t: Throwable) {
                Toast.makeText(context,t.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}