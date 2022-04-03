package com.example.quickpagingdemo.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quickpagingdemo.databinding.ItemQuoteLayoutBinding
import com.example.quickpagingdemo.models.Result

class QuotePagingAdapter : PagingDataAdapter<Result,QuotePagingAdapter.QuoteViewHolder>(COMPARATOR) {

    class QuoteViewHolder(itemView: ItemQuoteLayoutBinding):RecyclerView.ViewHolder(itemView.root){
        val quote=itemView.quote
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item=getItem(position)
        holder.quote.text=item?.content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding=ItemQuoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return QuoteViewHolder(binding)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}
