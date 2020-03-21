package io.github.jenthone.hackernews.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import io.github.jenthone.hackernews.R
import io.github.jenthone.hackernews.entity.Item
import io.github.jenthone.hackernews.helper.openLink
import io.github.jenthone.hackernews.helper.timeFormat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_story.*

class StoryAdapter(
    private val ids: List<Int>,
    private val items: MutableMap<Int, Item> = mutableMapOf(),
    private val shouldLoadData: (Int) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val id = ids[position]
        val item = items[id]
        if (item == null) {
            shouldLoadData(id)
        }
        holder.bind(items[id])
    }

    fun notify(item: Item) {
        items[item.id] = item
        notifyItemChanged(ids.indexOf(item.id))
    }
}

class ItemViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    companion object {
        fun create(parent: ViewGroup): ItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_story, parent, false)
            return ItemViewHolder(view)
        }
    }

    fun bind(item: Item?) {
        item ?: return

        tvTitle.text = item.title

        tvBy.text = item.by
        tvUrl.isVisible = item.url != null

        item.url?.let {
            tvUrl.text = Uri.parse(it).host
        }

        itemView.setOnClickListener {
            val url = item.url ?: return@setOnClickListener
            itemView.context.openLink(url)
        }

        tvTime.isVisible = item.time != null
        item.time?.let {
            tvTime.text = it.timeFormat()
        }
    }
}