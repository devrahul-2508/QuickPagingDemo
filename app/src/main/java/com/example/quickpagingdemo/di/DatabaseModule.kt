package com.example.quickpagingdemo.di

import android.content.Context
import androidx.room.Room
import com.example.quickpagingdemo.db.QuoteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun provideFakerDB(context: Context):QuoteDatabase{
        return Room.databaseBuilder(
            context,
            QuoteDatabase::class.java,
            "QuoteDB"
        ).build()
    }
}