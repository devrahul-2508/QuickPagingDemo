package com.example.quickpagingdemo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.quickpagingdemo.paging.QuotePagingSource
import com.example.quickpagingdemo.retrofit.QuoteApi
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val quoteApi: QuoteApi){

    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = {QuotePagingSource(quoteApi)}
    ).liveData
}