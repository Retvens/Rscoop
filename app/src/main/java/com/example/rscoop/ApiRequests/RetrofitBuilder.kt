package com.example.rscoop.ApiRequests

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val Base_url = "https://api-test-retvens.vercel.app/"
object RetrofitBuilder {

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Base_url)
        .build()
        .create(RetvensUrls::class.java)



}