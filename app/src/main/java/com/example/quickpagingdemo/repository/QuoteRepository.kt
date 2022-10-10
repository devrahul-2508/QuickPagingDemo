package com.example.quickpagingdemo.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.quickpagingdemo.db.QuoteDatabase
import com.example.quickpagingdemo.paging.QuotePagingSource
import com.example.quickpagingdemo.paging.QuoteRemoteMediator
import com.example.quickpagingdemo.retrofit.QuoteApi
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val quoteApi: QuoteApi,private val quoteDatabase: QuoteDatabase){

    @OptIn(ExperimentalPagingApi::class)
    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        remoteMediator = QuoteRemoteMediator(quoteApi,quoteDatabase),
        pagingSourceFactory = {quoteDatabase.quoteDao().getQuotes()}
    ).liveData
}