package com.example.quickpagingdemo.di

import android.content.Context
import com.example.quickpagingdemo.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [RetrofitModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)


}