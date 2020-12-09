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
import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.helper.observeNotNull
import io.github.jenthone.hackernews.helper.openLink

@AndroidEntryPoint
class StoryFragment : Fragment() {
    private var binding: FragmentStoryBinding? = null

    private val storyViewModel: StoryViewModel by viewModels()

    private lateinit var storyAdapter: StoryAdapter

    private lateinit var storyType: StoryType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storyType = StoryType.valueOf(arguments?.getString("type") ?: return)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStoryBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initViewModels()

        fetchData()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun initViews() {
        binding?.apply {
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
        storyViewModel.stories.observeNotNull(viewLifecycleOwner) { result ->
            binding?.srlItem?.isRefreshing = false
            val data = result.getOrNull() ?: return@observeNotNull

            initAdapter(data)
        }

        storyViewModel.item.observeNotNull(viewLifecycleOwner) { result ->
            val data = result.getOrNull() ?: return@observeNotNull
            binding?.rcvItem?.post {
                storyAdapter.notify(data)
            }
        }
    }

    private fun initAdapter(ids: List<Int>) {
        storyAdapter = StoryAdapter(
            ids,
            listener = object : StoryAdapterListener {
                override fun onBindEmptyItem(id: Int) {
                    storyViewModel.fetchItem(id)
                }

                override fun onOpenItemUrl(item: Item) {
                    context?.openLink(item.url ?: return)
                }

                override fun onOpenItemCreator(item: Item) {
                    context?.openLink("${Const.BASE_WEB_URL}user?id=${item.by}")
                }
            }
        )

        binding?.rcvItem?.adapter = storyAdapter
    }

    private fun fetchData() =
        storyViewModel.fetchItems(storyType)

    companion object {
        fun newInstance(type: String) = StoryFragment()
            .apply {
                arguments = Bundle().apply {
                    this.putString("type", type)
                }
            }
    }
}
