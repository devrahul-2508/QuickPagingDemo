package com.example.quickpagingdemo.di

import com.example.quickpagingdemo.retrofit.QuoteApi
import com.example.quickpagingdemo.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun providesRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provides(retrofit: Retrofit):QuoteApi{
         return retrofit.create(QuoteApi::class.java)
    }
}