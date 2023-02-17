package com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvence.rscoop.DataCollections.HotelsLocation
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProperty : AppCompatActivity() {

    private lateinit var clientCover:ImageView
    private lateinit var clientProfile:ImageView
    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001
    private  var value:Boolean = false
    private  var value1:Boolean = false
    private var profilePhotoPart: Uri? = null
    private  var coverPhotoPart: Uri?= null
    private lateinit var googleReviews: EditText
    private lateinit var tripAdvisorReview: EditText
    private lateinit var EnterCompleteAddress: EditText
    private lateinit var location: EditText
    private lateinit var propertyStarts: EditText
    private lateinit var hotelName:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)


        val backbtn = findViewById<ImageView>(R.id.addproperty_back_btn)
        backbtn.setOnClickListener {
            startActivity(Intent(this, AdminDashBoard::class.java))
            finish()
        }


        clientCover = findViewById(R.id.property_cover)
        val editPropertyCover = findViewById<ImageView>(R.id.editcover)
        clientProfile = findViewById<ImageView>(R.id.property_profile)
        val editPropertyProfile = findViewById<ImageView>(R.id.editprofile)
        googleReviews = findViewById<EditText>(R.id.property_google)
        tripAdvisorReview = findViewById<EditText>(R.id.property_trip)
        EnterCompleteAddress = findViewById<EditText>(R.id.property_address)
        location = findViewById<EditText>(R.id.property_location)
        propertyStarts = findViewById<EditText>(R.id.property_starts)
        hotelName = findViewById(R.id.hotel_name_text)

        editPropertyCover.setOnClickListener {
            pickImageFromGallery()
            value = true
            value1 = false
        }

        editPropertyProfile.setOnClickListener {
            pickImageFromGallery()
            value1 = true
            value = false
        }

        val save = findViewById<TextView>(R.id.save_property)
        save.setOnClickListener {

//            uploadData()
//            sendData()
        }

    }

//    private fun sendData() {
//        val reviews = googleReviews.text.toString().trim()
//        val trip = tripAdvisorReview.text.toString().trim()
//        val address = EnterCompleteAddress.text.toString().trim()
//        val location = location.text.toString().trim()
//        val country = propertyStarts.text.toString().trim()
//        val hotelName = hotelName.text.toString().trim()
//
//        val hotelLocations = listOf(
//            HotelsLocation("", "latitude_value_1", "longitude_value_1")
//        )
//
//        val send = HotelsData("","5050",hotelName,hotelLocations,"5","10","5","5252",
//        "https://th.bing.com/th?id=OIP.JPRrS4fGRYal6Kh61f0kfwHaEJ&w=334&h=187&c=8&rs=1&qlt=90&o=6&dpr=1.5&pid=3.1&rm=2",
//            "https://th.bing.com/th?id=OIP.JPRrS4fGRYal6Kh61f0kfwHaEJ&w=334&h=187&c=8&rs=1&qlt=90&o=6&dpr=1.5&pid=3.1&rm=2","abc123")
//
//        val data = RetrofitBuilder.retrofitBuilder.PostHotels(send)
//
//        data.enqueue(object : Callback<List<HotelsData>?> {
//            override fun onResponse(
//                call: Call<List<HotelsData>?>,
//                response: Response<List<HotelsData>?>
//            ) {
//                if(response.isSuccessful){
//                    Toast.makeText(this@AddProperty,"Data send", Toast.LENGTH_LONG).show()
//                }
//                else{
//                    Toast.makeText(applicationContext,response.code().toString(), Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onFailure(call: Call<List<HotelsData>?>, t: Throwable) {
//                Toast.makeText(this@AddProperty,t.message,Toast.LENGTH_LONG).show()
//                Log.e("error",t.message.toString())
//            }
//        })
//    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {


            if (value == true){

                clientCover.setImageURI(data?.data)
                coverPhotoPart = data?.data

            }
            else if (value1 == true){
                clientProfile.setImageURI(data?.data)
                profilePhotoPart = data?.data
            }

        }

    }
}