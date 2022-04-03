package com.example.quickpagingdemo.retrofit

import com.example.quickpagingdemo.models.QuoteList
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    @GET("/quotes")
    suspend fun getQuotes(
        @Query("page") page:Int
    ):QuoteList
}