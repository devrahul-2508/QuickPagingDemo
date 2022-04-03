package com.example.quickpagingdemo

import android.app.Application
import com.example.quickpagingdemo.di.ApplicationComponent
import com.example.quickpagingdemo.di.DaggerApplicationComponent

class QuoteApplication : Application(){
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent=DaggerApplicationComponent.builder().build()
    }
}