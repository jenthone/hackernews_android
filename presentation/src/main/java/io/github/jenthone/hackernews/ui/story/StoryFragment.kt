package io.github.jenthone.hackernews.ui.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.jenthone.hackernews.data.Const
import io.github.jenthone.hackernews.databinding.FragmentStoryBinding
import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.domain.helper.AsyncResult
import io.github.jenthone.hackernews.entity.Item
import io.github.jenthone.hackernews.helper.observeNonNull
import io.github.jenthone.hackernews.helper.openLink
import io.github.jenthone.hackernews.mapper.toPresentation
import io.github.jenthone.hackernews.viewmodel.ItemViewModel
import timber.log.Timber

@AndroidEntryPoint
class StoryFragment : Fragment() {
    companion object {
        fun newInstance(type: String) = StoryFragment()
            .apply {
                arguments = Bundle().apply {
                    this.putString("type", type)
                }
            }
    }

    private var binding: FragmentStoryBinding? = null
    private val requireBinding get() = requireNotNull(binding)

    private val vmItem: ItemViewModel by viewModels()

    private lateinit var adapter: StoryAdapter

    private lateinit var type: StoryType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        type = StoryType.valueOf(arguments?.getString("type") ?: return)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoryBinding.inflate(inflater, container, false)
        return requireBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initViewModels()

        fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initViews() {
        with(requireBinding) {
            rcvItem.layoutManager = LinearLayoutManager(context)
            rcvItem.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            rcvItem.setHasFixedSize(true)

            srlItem.setOnRefreshListener {
                fetchData()
            }
        }
    }

    private fun initViewModels() {
        vmItem.liveResultStories.observeNonNull(viewLifecycleOwner) { result ->
            requireBinding.srlItem.isRefreshing = false
            when (result) {
                is AsyncResult.Success -> {
                    Timber.d(result.data.toString())
                    initAdapter(result.data)
                }
                is AsyncResult.Error -> {
                    Timber.e(result.exception)
                }
            }
        }

        vmItem.liveResultItem.observeNonNull(viewLifecycleOwner) { result ->
            when (result) {
                is AsyncResult.Success -> {
                    requireBinding.rcvItem.post {
                        adapter.notify(result.data.toPresentation())
                    }
                }
            }
        }
    }

    private fun initAdapter(ids: List<Int>) {
        adapter = StoryAdapter(ids, listener = object : StoryAdapterListener {
            override fun onBindEmptyItem(id: Int) {
                vmItem.fetchItem(id)
            }

            override fun onOpenItemUrl(item: Item) {
                context?.openLink(item.url ?: return)
            }

            override fun onOpenItemCreator(item: Item) {
                context?.openLink("${Const.BASE_WEB_URL}user?id=${item.by}")
            }
        })

        requireBinding.rcvItem.adapter = adapter
    }

    private fun fetchData() {
        vmItem.fetchItems(type)
    }
}
