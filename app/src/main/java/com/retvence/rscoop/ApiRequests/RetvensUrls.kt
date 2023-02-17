package com.retvence.rscoop.DashBoard.DashBoard.AdminDashBoard.NavigationFragment

import com.retvence.rscoop.DataCollections.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
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
        @Part("_id") _id:RequestBody,
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
        @Part("__v") __v:RequestBody
        ): Call<List<OwnersData>>

    @GET("property/{owner_id}")
    fun getHotel(@Path("owner_id") owner:String): Call<List<HotelsData>>

    @GET("Country")
    fun getCountry(): Call<List<CountryData>>

    @GET("task")
    fun getTask(): Call<List<TaskData>>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(@Field("Email") email: String,
                  @Field("Password") password: String
    ) : Call<UserLoginData>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body user: UserLoginData): Call<LoginResponse>

    @FormUrlEncoded
    @POST("task")
    fun createSocialMeadia(
        @Field("facebook") facebook:String,
        @Field("Linkedin") Linkedin:String,
        @Field("instagram") instagram:String,
        @Field("twitter") twitter : String,
        @Field("Pinterest") Pinterest:String,
        @Field("GMB") GMB : String,
        @Field("Google_reviews") Google_reviews : String
    ) : Call<TaskData>
}