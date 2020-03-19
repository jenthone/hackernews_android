package io.github.jenthone.hackernews.helper.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.jenthone.hackernews.R

abstract class EndlessAdapter(protected val items: MutableList<Any> = mutableListOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_LOADING = 1
        const val VIEW_TYPE_NORMAL = 1000
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is LoadingItem -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOADING -> LoadMoreHolder.create(
                parent
            )
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun showLoading() {
        if (!isLoading()) {
            items.add(LoadingItem())
            notifyItemInserted(items.lastIndex)
        }
    }

    fun hideLoading() {
        if (isLoading()) {
            items.lastIndex.let {
                items.removeAt(it)
                notifyItemRemoved(it)
            }
        }
    }

    fun append(items: List<Any>) {
        hideLoading()
        val startPosition = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(startPosition, items.size)
    }

    fun addAll(items: List<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    private fun isLoading() = items.lastOrNull() is LoadingItem

    class LoadMoreHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun create(parent: ViewGroup): LoadMoreHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)
                return LoadMoreHolder(
                    view
                )
            }
        }

        fun bind() {

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoadMoreHolder) {
            holder.bind()
        }
    }

    class LoadingItem
}