package com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvence.rscoop.DataCollections.HotelsLocation
import com.retvence.rscoop.DataCollections.ResponseClient
import com.retvens.rscoop.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AddProperty : AppCompatActivity() {

    private lateinit var hotelCover:ImageView
    private lateinit var hotelProfile:ImageView
    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001
    private  var value:Boolean = false
    private  var value1:Boolean = false
    private var profilePhotoPart: Uri? = null
    private  var coverPhotoPart: Uri?= null
    private lateinit var edithotelCover:ImageView
    private lateinit var edithotelProfile:ImageView
    private lateinit var googleReviews: EditText
    private lateinit var tripAdvisorReview: EditText
    private lateinit var EnterCompleteAddress: EditText
    private lateinit var location: EditText
    private lateinit var propertyStarts: EditText
    private lateinit var hotelName:EditText
    private lateinit var About:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)


        val backbtn = findViewById<ImageView>(R.id.addproperty_back_btn)
        backbtn.setOnClickListener {
            onBackPressed()
        }

        About = findViewById(R.id.property_desc)
        hotelCover = findViewById(R.id.property_cover)
        val editPropertyCover = findViewById<ImageView>(R.id.editcover)
        hotelProfile = findViewById<ImageView>(R.id.property_profile)
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
            if (hotelName.text.isNotEmpty() && googleReviews.text.isNotEmpty() && tripAdvisorReview.text.isNotEmpty() && EnterCompleteAddress.text.isNotEmpty()
                && location.text.isNotEmpty() && propertyStarts.text.isNotEmpty() && About.text.isNotEmpty()){
                uploadData()
            }else{
                Toast.makeText(applicationContext,"Enter Proper Details",Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun uploadData() {

        val hotelName = hotelName.text.toString()
        val googleReview = googleReviews.text.toString()
        val tripAdvisor = tripAdvisorReview.text.toString()
        val address = EnterCompleteAddress.text.toString()
        val country = location.text.toString()
        val stars = propertyStarts.text.toString()
        val about = About.text.toString()

        if (coverPhotoPart == null){
            edithotelCover.snackbar("Select an Image First")
        }

        val parcelFileDescriptor = contentResolver.openFileDescriptor(
            coverPhotoPart!!,"r",null
        )?:return



        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir,contentResolver.getFileName(coverPhotoPart!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        val body = UploadRequestBody(file,"image")


        if (profilePhotoPart == null){
            edithotelProfile.snackbar("Select an Image First")
        }

        val parcelFileDescriptor1 = contentResolver.openFileDescriptor(
            profilePhotoPart!!,"r",null
        )?:return



        val inputStream1 = FileInputStream(parcelFileDescriptor1.fileDescriptor)
        val file1 = File(cacheDir,contentResolver.getFileName(profilePhotoPart!!))
        val outputStream1 = FileOutputStream(file1)
        inputStream1.copyTo(outputStream1)
        val body1 = UploadRequestBody(file1,"image")

        val hotelLocations = listOf(HotelsLocation(Latitude = "12.3456", Longitude = "23.4567"))

        val locationParts = mutableListOf<MultipartBody.Part>()

        hotelLocations.forEach { location ->
            val json = Gson().toJson(location)
            val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)
            val part = MultipartBody.Part.createFormData("hotel_location[]", null, requestBody)
            locationParts.add(part)
        }

        val send = RetrofitBuilder.retrofitBuilder.uploadHotelData(
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),hotelName),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),googleReview),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),stars),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),tripAdvisor),
            MultipartBody.Part.createFormData("hotel_logo", file1.name, body1),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),country),
            MultipartBody.Part.createFormData("Cover_photo", file.name, body),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),about),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),"qQL-RPghW"),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),address)
        )

        send.enqueue(object : Callback<ResponseClient?> {
            override fun onResponse(
                call: Call<ResponseClient?>,
                response: Response<ResponseClient?>
            ) {
                if (response.isSuccessful){
                    val response = response.body()!!
                    Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseClient?>, t: Throwable) {
                Toast.makeText(applicationContext,t.message.toString(),Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {


            if (value == true){

                hotelCover.setImageURI(data?.data)
                coverPhotoPart = data?.data

            }
            else if (value1 == true){
                hotelProfile.setImageURI(data?.data)
                profilePhotoPart = data?.data
            }

        }
    }


    private fun ContentResolver.getFileName(coverPhotoPart: Uri): String {

        var name = ""
        val returnCursor = this.query(coverPhotoPart,null,null,null,null)
        if (returnCursor!=null){
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

    private fun View.snackbar(s: String) {
        Snackbar.make(this, s, Snackbar.LENGTH_LONG).also { snackbar ->
            snackbar.setAction("Ok") {
                snackbar.dismiss()
            }
        }.show()
    }
}