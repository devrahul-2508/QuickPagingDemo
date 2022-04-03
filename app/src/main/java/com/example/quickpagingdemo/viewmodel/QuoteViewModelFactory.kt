package com.example.quickpagingdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quickpagingdemo.repository.QuoteRepository
import javax.inject.Inject

class QuoteViewModelFactory @Inject constructor(private val repository: QuoteRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuoteViewModel(repository) as T
    }
}