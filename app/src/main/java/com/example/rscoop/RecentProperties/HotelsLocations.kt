package com.retvens.rscoop.RecentProperties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.retvens.rscoop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelsLocations : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var map:MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotels_locations)

        val image = findViewById<ImageView>(R.id.individual_HotelImage)
        map = findViewById(R.id.mapView)
//        map.onCreate(savedInstanceState)
        map.getMapAsync(this)

        //getting data from intent

        val hotel_Name = intent.getStringExtra("item_name")
        val hotel_Image = intent.getStringExtra("item_image")

        Glide.with(baseContext).load(hotel_Image).into(image)
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        val latitude = 18.921729
        val longitude = 72.833031
        val location = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(location).title("San Francisco"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

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