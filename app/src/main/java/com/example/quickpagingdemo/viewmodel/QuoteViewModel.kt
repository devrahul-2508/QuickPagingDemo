package com.example.quickpagingdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.quickpagingdemo.models.Result
import com.example.quickpagingdemo.repository.QuoteRepository

class QuoteViewModel(repository: QuoteRepository):ViewModel() {
    val list:LiveData<PagingData<Result>> = repository.getQuotes().cachedIn(viewModelScope)
}
