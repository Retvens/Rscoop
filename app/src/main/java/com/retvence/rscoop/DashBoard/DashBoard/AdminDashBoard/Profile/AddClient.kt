package com.retvens.rscoop.DashBoard.DashBoard.AdminDashBoard.Profile

import android.app.appsearch.SetSchemaRequest.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.content.Intent
import android.widget.Toast
import com.retvens.rscoop.R

class AddClient : AppCompatActivity() {

    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001
    private lateinit var clientCover:ImageView
    private lateinit var clientProfile:ImageView
    private  var value:Boolean = false
    private  var value1:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)

        //define all the assets

        clientCover = findViewById<ImageView>(R.id.client_cover)
        val editClientCover = findViewById<ImageView>(R.id.edit_clientCover)
        clientProfile = findViewById<ImageView>(R.id.client_profile)
        val editClientProfile = findViewById<ImageView>(R.id.edit_clientProfile)
        val clientNumber = findViewById<TextView>(R.id.client_number)
        val clientMail = findViewById<TextView>(R.id.client_email)
        val clientPassword = findViewById<TextView>(R.id.client_pasword)
        val clientLocation = findViewById<TextView>(R.id.client_location)


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
                clientCover.setImageURI(data?.data)
            }
            else if (value1 == true){
                clientProfile.setImageURI(data?.data)
            }

        }
    }




}