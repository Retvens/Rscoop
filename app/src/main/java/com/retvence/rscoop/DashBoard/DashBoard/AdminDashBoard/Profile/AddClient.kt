package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DataCollections.OwnersData
import com.retvens.rscoop.R
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddClient : AppCompatActivity() {

    private lateinit var clientCover:ImageView
    private lateinit var clientProfile:ImageView
    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001
    private  var value:Boolean = false
    private  var value1:Boolean = false
    private var profilePhotoPart: Uri? = null
    private  var coverPhotoPart:Uri?= null
    private lateinit var clientNumber:EditText
    private lateinit var clientPassword:EditText
    private lateinit var clientLocation:EditText
    private lateinit var clientMail:EditText
    private lateinit var clientName:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)

        clientCover = findViewById(R.id.client_cover)
        val editClientCover = findViewById<ImageView>(R.id.edit_clientCover)
        clientProfile = findViewById<ImageView>(R.id.client_profile)
        val editClientProfile = findViewById<ImageView>(R.id.edit_clientProfile)
        clientNumber = findViewById<EditText>(R.id.client_phone)
         clientMail = findViewById<EditText>(R.id.client_email)
         clientPassword = findViewById<EditText>(R.id.client_pasword)
         clientLocation = findViewById<EditText>(R.id.client_location)
         clientName = findViewById<EditText>(R.id.client_name_text)


        editClientCover.setOnClickListener {
            pickImageFromGallery()
            value = true
            value1 = false
        }

        editClientProfile.setOnClickListener {
            pickImageFromGallery()
            value1 = true
            value = false
        }

        val backbtn = findViewById<ImageView>(R.id.add_back_btn)
        backbtn.setOnClickListener {
            startActivity(Intent(this, AdminDashBoard::class.java))
            finish()
        }

        val save = findViewById<TextView>(R.id.save)

        save.setOnClickListener {

//            uploadData()
            sendData()
        }

    }

    private fun sendData() {


        val name = clientName.text.toString().trim()
        val email = clientMail.text.toString().trim()
        val password = clientPassword.text.toString().trim()
        val phone = clientNumber.text.toString().trim()
        val country = clientLocation.text.toString().trim()

        val data = OwnersData("",name,email,password,phone,"0000","https://th.bing.com/th?id=OIP.JPRrS4fGRYal6Kh61f0kfwHaEJ&w=334&h=187&c=8&rs=1&qlt=90&o=6&dpr=1.5&pid=3.1&rm=2"
            ,"DM",country,"abc123","https://th.bing.com/th?id=OIP.JPRrS4fGRYal6Kh61f0kfwHaEJ&w=334&h=187&c=8&rs=1&qlt=90&o=6&dpr=1.5&pid=3.1&rm=2")

        val send = RetrofitBuilder.retrofitBuilder.PostOwner(data)


        send.enqueue(object : Callback<List<OwnersData>?> {
            override fun onResponse(
                call: Call<List<OwnersData>?>,
                response: Response<List<OwnersData>?>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@AddClient,"ok",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<List<OwnersData>?>, t: Throwable) {
                Toast.makeText(this@AddClient,t.message,Toast.LENGTH_LONG).show()
                Log.e("error",t.message.toString())
            }
        })

    }

//    private fun uploadData() {
//
//        val name = clientName.text.toString().toString()
//        val email = clientMail.text.toString()
//        val password = clientPassword.text.toString()
//        val phone = clientNumber.text.toString()
//        val country = clientLocation.text.toString()
//
//
//        val profilePhoto = if (profilePhotoPart != null) {
//
//            val file = File(getRealPathFromURI(profilePhotoPart!!))
//            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//            MultipartBody.Part.createFormData("Profile_photo", file.name, requestFile)
//        } else {
//            null
//        }
//
//        Log.e("",profilePhoto.toString())
//        val coverPhoto = if (coverPhotoPart != null) {
//            val file = File(getRealPathFromURI(coverPhotoPart!!))
//            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//            MultipartBody.Part.createFormData("Cover_photo", file.name, requestFile)
//        } else {
//            null
//        }
//        Log.e("",profilePhoto.toString())
//        val send = RetrofitBuilder.retrofitBuilder.uploadData(
//            "",name,email,password, 123456,"123",profilePhoto!!,"",country,"abc123",coverPhoto!!,0
//        )
//        send.enqueue(object : Callback<List<OwnersData>?> {
//            override fun onResponse(
//                call: Call<List<OwnersData>?>,
//                response: Response<List<OwnersData>?>
//            ) {
//                if(response.isSuccessful){
//                    Toast.makeText(this@AddClient,"ok",Toast.LENGTH_LONG).show()
//                }
//                else{
//                    Toast.makeText(this@AddClient,"not ok",Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onFailure(call: Call<List<OwnersData>?>, t: Throwable) {
//                Toast.makeText(this@AddClient,t.message,Toast.LENGTH_LONG).show()
//                Log.e("error",t.message.toString())
//            }
//        })
//
//
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
    private fun getRealPathFromURI(uri: Uri): String {
        var result = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                result = cursor.getString(columnIndex)
            }
            cursor.close()
        }
        Log.e("",result.toString())
        return result

    }
}