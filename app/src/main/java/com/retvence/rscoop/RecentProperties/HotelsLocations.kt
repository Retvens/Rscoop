package com.retvens.rscoop.RecentProperties

import android.content.Intent
import android.net.Uri
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

    private lateinit var facebookLink: ImageView
    private lateinit var googleLink: ImageView
    private lateinit var instagramLink: ImageView
    private lateinit var linkedinLink: ImageView
    private lateinit var pinterestLink: ImageView
    private lateinit var tripodvisorLink: ImageView
    private lateinit var whatsappLink: ImageView
    private lateinit var twitterLink: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotels_locations)

        val backbtn = findViewById<ImageView>(R.id.hotels_back)
        backbtn.setOnClickListener {
            startActivity(Intent(this, AdminDashBoard::class.java))
            finish()
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
        val latitude = intent.getStringExtra("latitude")
        val longitude = intent.getStringExtra("longitude")
        val logoOfHotel = intent.getStringExtra("logo")

        val lat = latitude!!.toDouble()
        val long = longitude!!.toDouble()



        facebookLink = findViewById(R.id.facebookLink)
        googleLink = findViewById(R.id.googleLink)
        instagramLink = findViewById(R.id.instagramLink)
        linkedinLink = findViewById(R.id.linkdinLink)
        pinterestLink = findViewById(R.id.pinterestLink)
        tripodvisorLink = findViewById(R.id.tripadivisorLink)
        whatsappLink = findViewById(R.id.whatsappLink)
        twitterLink = findViewById(R.id.twitterLink)

        facebookLink.setOnClickListener {
            val uriForFB : Uri = Uri.parse("https://facebook.com")
            startActivity(Intent(Intent.ACTION_VIEW,uriForFB))
        }
        googleLink.setOnClickListener {
            val uriForGoo :Uri = Uri.parse("https://google.com")
            startActivity(Intent(Intent.ACTION_VIEW,uriForGoo))
        }
        instagramLink.setOnClickListener {
            val uriForInsta :Uri = Uri.parse("https://instagram.com")
            startActivity(Intent(Intent.ACTION_VIEW,uriForInsta))
        }
        linkedinLink.setOnClickListener {
            val uriForLink :Uri = Uri.parse("https://linkedin.com")
            startActivity(Intent(Intent.ACTION_VIEW,uriForLink))
        }
        pinterestLink.setOnClickListener {
            val uriForPint :Uri = Uri.parse("https://pinterest.com")
            startActivity(Intent(Intent.ACTION_VIEW,uriForPint))
        }
        tripodvisorLink.setOnClickListener {
            val uriForTri :Uri = Uri.parse("https://tripadvisor.com")
            startActivity(Intent(Intent.ACTION_VIEW,uriForTri))
        }
        whatsappLink.setOnClickListener {
            val uriForWhat :Uri = Uri.parse("https://web.whatsapp.com")
            startActivity(Intent(Intent.ACTION_VIEW,uriForWhat))
        }
        twitterLink.setOnClickListener {
            val uriFoTwi :Uri = Uri.parse("https://twitter.com")
            startActivity(Intent(Intent.ACTION_VIEW,uriFoTwi))
        }

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
                val location = LatLng(lat, long)
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