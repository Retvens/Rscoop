package com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

import com.retvence.rscoop.DataCollections.CountryData
import com.retvence.rscoop.DataCollections.HotelsData
import com.retvence.rscoop.DataCollections.OwnersData
import com.retvence.rscoop.DataCollections.TaskData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.Objects


interface RetvensUrls {

    @GET("hotelowner/{Country}")
    fun getOwner(@Path("Country") Country:String): Call<List<OwnersData>>

    @POST("hotelowner")
    fun PostOwner(@Body ownerData: OwnersData ): Call<List<OwnersData>>

    @Multipart
    @POST("hotelowner")
    fun uploadData(
        @Part("_id") _id:String,
        @Part("Name") name: String,
        @Part("Email") email: String,
        @Part("Password") password: String,
        @Part("Phone") phone: String,
        @Part("owner_id") ownerId: String,
        @Part profilePhoto: MultipartBody.Part,
        @Part("Service_type") type: String,
        @Part("Country") country: String,
        @Part("token") token: String,
        @Part coverPhoto: MultipartBody.Part,

    ): Call<List<OwnersData>>

    @GET("property/{owner_id}")
    fun getHotel(@Path("owner_id") owner:String): Call<List<HotelsData>>

    @GET("Country")
    fun getCountry(): Call<List<CountryData>>

    @GET("task")
    fun getTask(): Call<List<TaskData>>

}