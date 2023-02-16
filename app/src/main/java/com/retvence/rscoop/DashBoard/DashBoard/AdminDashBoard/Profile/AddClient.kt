package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile

import android.app.Activity
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
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile.UploadRequestBody
import com.retvence.rscoop.DataCollections.OwnersData
import com.retvens.rscoop.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AddClient : AppCompatActivity(), UploadRequestBody.UploadCallback {

    private lateinit var clientCover:ImageView
    private lateinit var clientProfile:ImageView
    private val IMAGE_PICK_CODE = 101
    private val PERMISSION_CODE = 1001
    private  var value:Boolean = false
    private  var value1:Boolean = false
    private var profilePhotoPart: Uri? = null
    private  var coverPhotoPart:Uri?= null
    private lateinit var editClientCover:ImageView
    private lateinit var editClientProfile:ImageView
    private lateinit var clientNumber:EditText
    private lateinit var clientPassword:EditText
    private lateinit var clientLocation:EditText
    private lateinit var clientMail:EditText
    private lateinit var clientName:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)

        clientCover = findViewById(R.id.client_cover)
        editClientCover = findViewById<ImageView>(R.id.edit_clientCover)
        clientProfile = findViewById<ImageView>(R.id.client_profile)
        editClientProfile = findViewById<ImageView>(R.id.edit_clientProfile)
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

            uploadData()
//            sendData()
        }

    }

//    private fun sendData() {
//
//
//        val name = clientName.text.toString().trim()
//        val email = clientMail.text.toString().trim()
//        val password = clientPassword.text.toString().trim()
//        val phone = clientNumber.text.toString().trim()
//        val country = clientLocation.text.toString().trim()
//
//        val data = OwnersData("",name,email,password,phone,"0000","https://th.bing.com/th?id=OIP.JPRrS4fGRYal6Kh61f0kfwHaEJ&w=334&h=187&c=8&rs=1&qlt=90&o=6&dpr=1.5&pid=3.1&rm=2"
//            ,"DM",country,"abc123","https://th.bing.com/th?id=OIP.JPRrS4fGRYal6Kh61f0kfwHaEJ&w=334&h=187&c=8&rs=1&qlt=90&o=6&dpr=1.5&pid=3.1&rm=2")
//
//        val send = RetrofitBuilder.retrofitBuilder.PostOwner(data)
//
//
//        send.enqueue(object : Callback<List<OwnersData>?> {
//            override fun onResponse(
//                call: Call<List<OwnersData>?>,
//                response: Response<List<OwnersData>?>
//            ) {
//                if(response.isSuccessful){
//                    Toast.makeText(this@AddClient,"ok",Toast.LENGTH_LONG).show()
//                }
//                else{
//                    Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_LONG).show()
//                }
//
//            }
//
//            override fun onFailure(call: Call<List<OwnersData>?>, t: Throwable) {
//                Toast.makeText(this@AddClient,t.message,Toast.LENGTH_LONG).show()
//                Log.e("error",t.message.toString())
//            }
//        })
//
//    }

    private fun uploadData() {

        val name = clientName.text.toString().trim()
        val email = clientMail.text.toString().trim()
        val password = clientPassword.text.toString().trim()
        val phone = clientNumber.text.toString().trim()
        val country = clientLocation.text.toString().trim()


       if (coverPhotoPart == null){
           editClientCover.snackbar("Select an Image First")
       }

        val parcelFileDescriptor = contentResolver.openFileDescriptor(
            coverPhotoPart!!,"r",null
        )?:return



        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir,contentResolver.getFileName(coverPhotoPart!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        val body = UploadRequestBody(file,"Profile_photo",this)


        Log.e("", file.name.toString())
        val send = RetrofitBuilder.retrofitBuilder
        send.uploadData(
            RequestBody.create("application/json".toMediaTypeOrNull(), ""),
            RequestBody.create("application/json".toMediaTypeOrNull(), name),
            RequestBody.create("application/json".toMediaTypeOrNull(), email),
            RequestBody.create("application/json".toMediaTypeOrNull(), password),
            RequestBody.create("application/json".toMediaTypeOrNull(), phone),
            RequestBody.create("application/json".toMediaTypeOrNull(), "000"),
            MultipartBody.Part.createFormData("Profile_photo",file.name,body),
            RequestBody.create("application/json".toMediaTypeOrNull(), ""),
            RequestBody.create("application/json".toMediaTypeOrNull(), country),
            RequestBody.create("application/json".toMediaTypeOrNull(), "abc123"),
            MultipartBody.Part.createFormData("Profile_photo",file.name,body),
            RequestBody.create("application/json".toMediaTypeOrNull(), "")
        )
            .enqueue(object : Callback<List<OwnersData>?> {
            override fun onResponse(
                call: Call<List<OwnersData>?>,
                response: Response<List<OwnersData>?>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(applicationContext,"success",Toast.LENGTH_LONG).show()
                }else{
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (errorBody.isNullOrEmpty()) {
                        "Unknown error"
                    } else {
                        try {

                            val json = JSONObject(errorBody)
                            json.getString("message")
                        } catch (e: Exception) {
                            errorBody
                        }
                    }
                    Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
                    Log.e("API Error", errorMessage)
                }
            }

            override fun onFailure(call: Call<List<OwnersData>?>, t: Throwable) {
                Toast.makeText(applicationContext,"${t.message.toString()}",Toast.LENGTH_LONG).show()
                Log.e("",t.message.toString())
            }
        })



    }

    private fun pickImageFromGallery() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val imageType = arrayOf("image/jpeg","image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES,imageType)
            startActivityForResult(it,IMAGE_PICK_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {


            if (value == true){

                coverPhotoPart = data?.data
                clientCover.setImageURI(coverPhotoPart)

            }
            else if (value1 == true){
               profilePhotoPart = data?.data
                clientProfile.setImageURI(profilePhotoPart)
            }

        }

    }

    override fun onProgressUpdate(percentage: Int) {
        Toast.makeText(this,"loading",Toast.LENGTH_LONG).show()

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

 // content://media/external/images/media/1425

