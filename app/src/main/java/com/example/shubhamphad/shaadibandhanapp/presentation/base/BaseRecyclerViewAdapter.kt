package com.example.shubhamphad.shaadibandhanapp.presentation.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<T, VB : ViewBinding>(
    private var items: List<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading = false
    private var hasMore = true

    companion object {
        private const val ITEM = 0
        private const val FOOTER = 1
    }
    abstract fun createBinding(parent: ViewGroup): VB
    abstract fun bindData(binding: VB, item: T, position: Int)
    abstract fun createLoadingView(parent: ViewGroup): ViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM) {
            val binding = createBinding(parent)
            BaseViewHolder(binding)
        } else {
            val binding = createLoadingView(parent)
            FooterViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseViewHolder<*> && position < items.size) {
            @Suppress("UNCHECKED_CAST")
            bindData(holder.binding as VB, items[position], position)
        }
    }

    override fun getItemCount(): Int {
        return items.size + if (isLoading && hasMore) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < items.size) ITEM else FOOTER
    }

    fun updateData(newItems: List<T>, hasMoreItems: Boolean) {
        this.items = newItems.toMutableList()
        this.hasMore = hasMoreItems
        this.isLoading = false
        notifyDataSetChanged()
    }

    fun addLoadingFooter() {
        if (!isLoading && hasMore) {
            isLoading = true
            notifyItemInserted(items.size)
        }
    }

    fun removeLoadingFooter() {
        if (isLoading) {
            isLoading = false
            notifyItemRemoved(items.size)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<T>) {
        items = newItems
        notifyDataSetChanged()
    }

    class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
    class FooterViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

}