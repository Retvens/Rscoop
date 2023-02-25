package com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

import com.retvence.rscoop.DashBoardClient.ClientProfileData
import com.retvence.rscoop.DashBoardIgniter.RecentPropertiesDataClass
import com.retvence.rscoop.DataCollections.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface RetvensUrls {

    @GET("hotelowner/{Country}")
    fun getOwner(@Path("Country") Country:String): Call<List<OwnersData>>

    @POST("hotelowner")
    fun PostOwner(@Body owner: OwnersData ): Call<List<OwnersData>>

    @POST("hotelowner")
    fun PostHotels(@Body data: HotelsData ): Call<List<HotelsData>>


    @Multipart
    @Headers("Content-Type: application/json")
    @POST("hotelowner")
    fun uploadData(
        @Part("Name") Name: RequestBody,
        @Part("Email") Email: RequestBody,
        @Part("Password") Password: RequestBody,
        @Part("Phone") Phone: RequestBody,
        @Part("owner_id") owner_id: RequestBody,
        @Part profilePhoto: MultipartBody.Part,
        @Part("Service_type") Service_type: RequestBody,
        @Part("Country") Country: RequestBody,
        @Part("token") token: RequestBody,
        @Part coverPhoto: MultipartBody.Part,
        ): Call<List<ResponseClient>>

    @GET("property/{owner_id}")
    fun getHotel(@Path("owner_id") owner:String): Call<List<HotelsData>>

    @GET("Country")
    fun getCountry(): Call<List<CountryData>>


    @GET("tasks")
    fun getTask(): Call<List<GetTaskData>>

    @POST("login")
    fun userLogin(
        @Body login:UserLoginData
    ) : Call<UserLoginData>

    @GET("property")
    fun getRecentProperty() : Call<List<RecentPropertiesDataClass>>

    @POST("tasks")
    fun createSocialMeadia(
        @Body social : TaskData
    ) : Call<ResponseTask>



    @PATCH("tasks/{id}")
    fun updateTask(
        @Path("id") id:String,
        @Body task:UpdateTaskClass
    ):Call<ResponseTask>


    @GET("tasks/{hotel_id}")
    fun individualTask(
        @Path("hotel_id") hotel_id:String
    ):Call<List<GetTaskData>>

    @PATCH("tasks/{id}")
    fun updateStatus(
        @Path("id") id:String,
        @Body task:StatusClass
    ):Call<ResponseTask>


    @GET("tasks/{Status}")
    fun completeTask(
        @Path("Status") Status:String
    ):Call<List<GetTaskData>>

    @GET("tasks/{Date}")
    fun TodayTask(
        @Path("Date") Date:String
    ):Call<List<GetTaskData>>

    @GET("hotelowner/{owner_id}")
    fun getClient(@Path("owner_id") owner_id: String ): Call<ClientProfileData>

}