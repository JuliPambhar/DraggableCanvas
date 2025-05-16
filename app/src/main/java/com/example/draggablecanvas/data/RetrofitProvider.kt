package com.example.draggablecanvas.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    fun getOverlayService() = Retrofit.Builder()
        .baseUrl("https://appostropheanalytics.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IOverlayService::class.java)
}