package com.retvens.rscoop.RecentProperties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.AdminDashBoard
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.retvens.rscoop.R

class HotelsLocations : AppCompatActivity() {

    private lateinit var googleMap: GoogleMap
    private lateinit var map:MapView
    private lateinit var mMapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotels_locations)

        val backbtn = findViewById<ImageView>(R.id.hotels_back)
        backbtn.setOnClickListener {
            startActivity(Intent(this, AdminDashBoard::class.java))
        }

        mMapFragment = SupportMapFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.map_show, mMapFragment)
                .commit()
//
//
//        //getting data from intent
        val hotel_Name = intent.getStringExtra("Name")
        val hotel_Image = intent.getStringExtra("image")
        val latitude = intent.getDoubleExtra("latitude",0.0)
        val longitude = intent.getDoubleExtra("longitude",0.0)
        val logoOfHotel = intent.getStringExtra("logo")


//

        val image = findViewById<ImageView>(R.id.clienthotelimg)
        val logo = findViewById<ImageView>(R.id.logoOfHotel)
        val name = findViewById<TextView>(R.id.Hotel_Name)
        val name2 = findViewById<TextView>(R.id.Hotel_Name2)

        Glide.with(baseContext).load(logoOfHotel).into(logo)
        name.text = hotel_Name
        name2.text = hotel_Name
        Glide.with(baseContext).load(hotel_Image).into(image)
        mMapFragment.getMapAsync(object : OnMapReadyCallback{
            override fun onMapReady(p0: GoogleMap) {
                googleMap = p0

                val location = LatLng(latitude, longitude)
                googleMap.addMarker(MarkerOptions().position(location).title("$hotel_Name"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
            }

        })
    }

//    override fun onMapReady(p0: GoogleMap) {

//    }

//    override fun onResume() {
//        super.onResume()
//        map.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        map.onPause()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        map.onDestroy()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        map.onLowMemory()
//    }



}