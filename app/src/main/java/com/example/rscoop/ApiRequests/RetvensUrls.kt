package com.example.rscoop.ApiRequests

import com.example.rscoop.DataCollections.CountryData
import com.example.rscoop.DataCollections.HotelsData
import com.example.rscoop.DataCollections.OwnersData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface RetvensUrls {

    @GET("hotelowner")
    fun getOwner(): Call<List<OwnersData>>

    @GET("/property")
    fun getHotel(): Call<List<HotelsData>>

    @GET("Country")
    fun getCountry(): Call<List<CountryData>>

}