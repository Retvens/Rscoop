package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile

import android.app.Dialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile.SelectClient
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile.UploadRequestBody
import com.retvence.rscoop.DataCollections.ResponseClient
import com.retvens.rscoop.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AddClient : AppCompatActivity(){

    private lateinit var clientCover:ImageView
    private lateinit var clientProfile:ImageView
    private val IMAGE_PICK_CODE = 101
    private val PERMISSION_CODE = 1001
    private val COVER_IMAGE_PICK_CODE = 1
    private val PROFILE_IMAGE_PICK_CODE = 2
    private val REQUEST_CODE_IMAGE_CROPPER = 1002
    private  var value:Boolean = false
    private  var value1:Boolean = false
    private var profilePhotoPart: Uri? = null
    private  var coverPhotoPart:Uri?= null
    private val selectedImages = mutableListOf<Uri>()
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

        val next = findViewById<LinearLayout>(R.id.nextActivity)

        next.setOnClickListener {
            startActivity(Intent(this,SelectClient::class.java))
        }

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

            if (clientName.text.isNotEmpty() && clientMail.text.isNotEmpty() && clientPassword.text.isNotEmpty() && clientNumber.text.isNotEmpty() && clientLocation.text.isNotEmpty()){
                uploadData()
            }else{
                Toast.makeText(applicationContext,"Enter Proper Details",Toast.LENGTH_LONG).show()
            }


        }

    }

//

    private fun uploadData() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialoge_progress)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val name = clientName.text.toString().trim()
        val email = clientMail.text.toString().trim()
        val password = clientPassword.text.toString().trim()
        val phone = clientNumber.text.toString().trim()
        val country = clientLocation.text.toString().trim()
        val token = "abc123"
        val serviceType = "DM"


       if (coverPhotoPart == null){
           editClientCover.snackbar("Select an Image First")
       }

        val parcelFileDescriptor = contentResolver.openFileDescriptor(
            coverPhotoPart!!,"r",null
        )?:return



        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, "cropped_${contentResolver.getFileName(coverPhotoPart!!)}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        val body = UploadRequestBody(file,"image")


        if (profilePhotoPart == null){
            editClientCover.snackbar("Select an Image First")
        }

        val parcelFileDescriptor1 = contentResolver.openFileDescriptor(
            profilePhotoPart!!,"r",null
        )?:return



        val inputStream1 = FileInputStream(parcelFileDescriptor1.fileDescriptor)
        val file1 = File(cacheDir, contentResolver.getFileName(profilePhotoPart!!) + ".jpg")
        val outputStream1 = FileOutputStream(file1)
        inputStream1.copyTo(outputStream1)
        val body1 = UploadRequestBody(file1,"image")


        val send = RetrofitBuilder.retrofitBuilder.uploadData(
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),name),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),email),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),password),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),phone),
            MultipartBody.Part.createFormData("Cover_photo", file.name, body),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),"DM"),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(),country),
            MultipartBody.Part.createFormData("Profile_photo", file1.name, body1)
        )

       send.enqueue(object : Callback<ResponseClient?> {
           override fun onResponse(
               call: Call<ResponseClient?>,
               response: Response<ResponseClient?>
           ) {
               if (response.isSuccessful){
                   val response = response.body()!!
                   dialog.dismiss()
                   Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG).show()
                   val intent = Intent(this@AddClient,AdminDashBoard::class.java)
                   intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                   startActivity(intent)

               }else{
                   Toast.makeText(applicationContext,response.code().toString(),Toast.LENGTH_LONG).show()
                   dialog.dismiss()
               }
           }

           override fun onFailure(call: Call<ResponseClient?>, t: Throwable) {
               Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
               dialog.dismiss()
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
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                val options = CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                if (value == true) {
                    options.setAspectRatio(16, 9)
                        .start(this)
                } else if (value1 == true) {
                    options.setAspectRatio(1, 1)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(this)
                }
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val croppedUri = result.uri
                if (value == true) {
                    clientCover.setImageURI(croppedUri)
                    coverPhotoPart = croppedUri // save the URI of the cropped image
                } else if (value1 == true) {
                    clientProfile.setImageURI(croppedUri)
                    profilePhotoPart = croppedUri // save the URI of the cropped image
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Log.e("ImageCropper", "Error cropping image: ${error.message}", error)
            }
            // reset the flags after handling the result
            value = false
            value1 = false
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



 // content://media/external/images/media/1425

