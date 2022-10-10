package com.example.quickpagingdemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quickpagingdemo.models.QuoteRemoteKeys

@Database(entities = [Result::class,QuoteRemoteKeys::class], version = 1)
abstract class QuoteDatabase: RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}