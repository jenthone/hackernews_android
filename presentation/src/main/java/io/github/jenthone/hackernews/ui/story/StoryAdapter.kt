package io.github.jenthone.hackernews.ui.story

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import io.github.jenthone.hackernews.R
import io.github.jenthone.hackernews.data.helper.glide.loadThumbnail
import io.github.jenthone.hackernews.entity.Item
import io.github.jenthone.hackernews.helper.timeFormat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_story.*

class StoryAdapter(
    private val ids: List<Int>,
    private val items: MutableMap<Int, Item> = mutableMapOf(),
    private val listener: StoryAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_STORY_ITEM = 0
        const val TYPE_STORY_EMPTY = 100
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_STORY_EMPTY) {
            return StoryEmptyViewHolder.create(parent)
        }
        return StoryViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[ids[position]]
        return if (item == null) {
            TYPE_STORY_EMPTY
        } else {
            TYPE_STORY_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val id = ids[position]
        val item = items[id]

        when (holder) {
            is StoryViewHolder -> {
                holder.bind(item, listener)
            }
            is StoryEmptyViewHolder -> {
                holder.bind(id, listener)
            }
        }
    }

    fun notify(item: Item) {
        items[item.id] = item
        notifyItemChanged(ids.indexOf(item.id))
    }
}

class StoryViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    companion object {
        fun create(parent: ViewGroup): StoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_story, parent, false)
            return StoryViewHolder(view)
        }
    }

    fun bind(item: Item?, listener: StoryAdapterListener) {
        item ?: return

        tvTitle.text = item.title

        tvBy.text = item.by

        tvUrl.isVisible = item.url != null
        item.url?.let {
            tvUrl.text = Uri.parse(it).host
        }

        imvThumbnail.loadThumbnail(item.url.orEmpty())

        tvTime.isVisible = item.time != null
        item.time?.let {
            tvTime.text = it.timeFormat()
        }

        tvUrl.setOnClickListener {
            listener.onOpenItemUrl(item)
        }

        imvThumbnail.setOnClickListener {
            listener.onOpenItemUrl(item)
        }

        tvBy.setOnClickListener {
            listener.onOpenItemCreator(item)
        }

        val score = item.score ?: 0
        tvScore.text = "$score"

        val descendants = item.descendants ?: 0

        tvComment.isVisible = descendants > 0
        tvComment.text = "$descendants"
    }
}

class StoryEmptyViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    companion object {
        fun create(parent: ViewGroup): StoryEmptyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_story_empty, parent, false)
            return StoryEmptyViewHolder(view)
        }
    }

    fun bind(id: Int, listener: StoryAdapterListener) {
        listener.onBindEmptyItem(id)
    }
}

interface StoryAdapterListener {
    fun onBindEmptyItem(id: Int)

    fun onOpenItemUrl(item: Item)

    fun onOpenItemCreator(item: Item)
}
