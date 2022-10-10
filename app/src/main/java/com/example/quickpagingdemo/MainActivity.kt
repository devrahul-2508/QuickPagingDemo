package com.example.quickpagingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickpagingdemo.databinding.ActivityMainBinding
import com.example.quickpagingdemo.di.DaggerApplicationComponent
import com.example.quickpagingdemo.paging.LoadAdapter
import com.example.quickpagingdemo.paging.QuotePagingAdapter
import com.example.quickpagingdemo.viewmodel.QuoteViewModel
import com.example.quickpagingdemo.viewmodel.QuoteViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var quoteViewModel: QuoteViewModel
    private lateinit var  quoteAdapter:QuotePagingAdapter


    @Inject
    lateinit var quoteViewModelFactory: QuoteViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        (application as QuoteApplication).applicationComponent.inject(this)

        quoteViewModel= ViewModelProvider(this,quoteViewModelFactory)[QuoteViewModel::class.java]

        quoteAdapter=QuotePagingAdapter()
        binding.quoteList.layoutManager=LinearLayoutManager(this)
        binding.quoteList.adapter=quoteAdapter.withLoadStateHeaderAndFooter(
            header = LoadAdapter(),
            footer = LoadAdapter()
        )

        getQuotes()
    }




    private fun getQuotes() {


        quoteViewModel.list.observe(this) {
            quoteAdapter.submitData(lifecycle, it)
            Log.i("Data","$it")
        }
    }
}