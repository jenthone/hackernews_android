package io.github.jenthone.hackernews.ui.story

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import io.github.jenthone.hackernews.R
import io.github.jenthone.hackernews.data.helper.glide.loadThumbnail
import io.github.jenthone.hackernews.databinding.ItemStoryBinding
import io.github.jenthone.hackernews.databinding.ItemStoryEmptyBinding
import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.helper.timeFormat

class StoryAdapter(
    private val ids: List<Int>,
    private val items: MutableMap<Int, Item> = mutableMapOf(),
    private val listener: StoryAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_STORY_EMPTY) {
            StoryEmptyViewHolder.create(parent)
        } else {
            StoryViewHolder.create(parent)
        }
    }

    override fun getItemCount(): Int = ids.size

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

        when (holder) {
            is StoryViewHolder -> {
                items[id]?.let { holder.bind(it, listener) }
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

    companion object {
        const val TYPE_STORY_ITEM = 0
        const val TYPE_STORY_EMPTY = 100
    }
}

class StoryViewHolder(private val binding: ItemStoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup): StoryViewHolder {
            val binding =
                ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return StoryViewHolder(binding)
        }
    }

    fun bind(item: Item, listener: StoryAdapterListener) {
        binding.tvTitle.text = item.title

        binding.tvBy.text = item.by

        binding.tvUrl.isVisible = item.url != null
        item.url?.let {
            binding.tvUrl.text = Uri.parse(it).host
        }

        binding.imvThumbnail.loadThumbnail(item.url.orEmpty(), R.mipmap.ic_launcher)

        binding.tvTime.isVisible = item.time != null
        item.time?.let {
            binding.tvTime.text = it.timeFormat()
        }

        binding.tvUrl.setOnClickListener {
            listener.onOpenItemUrl(item)
        }

        binding.imvThumbnail.setOnClickListener {
            listener.onOpenItemUrl(item)
        }

        binding.tvBy.setOnClickListener {
            listener.onOpenItemCreator(item)
        }

        val score = item.score ?: 0
        binding.tvScore.text = "$score"

        val descendants = item.descendants ?: 0

        binding.tvComment.isVisible = descendants > 0
        binding.tvComment.text = "$descendants"
    }
}

class StoryEmptyViewHolder(binding: ItemStoryEmptyBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup): StoryEmptyViewHolder {
            val binding =
                ItemStoryEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return StoryEmptyViewHolder(binding)
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
