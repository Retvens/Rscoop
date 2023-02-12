package com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

import com.retvence.rscoop.DataCollections.CountryData
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvence.rscoop.DataCollections.OwnersData
import com.retvence.rscoop.DataCollections.TaskData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface RetvensUrls {

    @GET("hotelowner/{Country}")
    fun getOwner(@Path("Country") Country:String): Call<List<OwnersData>>

    @GET("property/{owner_id}")
    fun getHotel(@Path("owner_id") owner:String): Call<List<HotelsData>>

    @GET("Country")
    fun getCountry(): Call<List<CountryData>>

    @GET("task")
    fun getTask(): Call<List<TaskData>>

}