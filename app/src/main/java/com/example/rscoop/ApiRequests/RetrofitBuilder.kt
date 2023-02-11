package com.example.rscoop.ApiRequests

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val Base_url = "https://api-test-retvens.vercel.app/"
object RetrofitBuilder {

    val gson = GsonBuilder().setLenient().create()

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(Base_url)
        .build()
        .create(RetvensUrls::class.java)

    val retrofitBuilder2 =  Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .build()
        .create(RetvensUrls::class.java)



}