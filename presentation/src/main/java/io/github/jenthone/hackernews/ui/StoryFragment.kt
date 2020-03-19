package io.github.jenthone.hackernews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import io.github.jenthone.hackernews.R
import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.domain.helper.AsyncResult
import io.github.jenthone.hackernews.mapper.toPresentation
import io.github.jenthone.hackernews.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.fragment_story.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class StoryFragment : Fragment() {
    companion object {
        fun newInstance(type: String) = StoryFragment().apply {
            arguments = Bundle().apply {
                this.putString("type", type)
            }
        }
    }


    private val vmItem by viewModel<ItemViewModel>()

    private lateinit var adapter: StoryAdapter

    private lateinit var type: StoryType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        type = StoryType.valueOf(arguments?.getString("type") ?: return)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_story, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initViewModels()

        fetchData()
    }

    private fun initViews() {
        rcvItem.layoutManager = LinearLayoutManager(context)
        rcvItem.setHasFixedSize(true)

        srlItem.setOnRefreshListener {
            fetchData()
        }
    }

    private fun initViewModels() {
        vmItem.resultStories.observe(viewLifecycleOwner, Observer { result ->
            srlItem.isRefreshing = false
            when (result) {
                is AsyncResult.Success -> {
                    Timber.d(result.data.toString())
                    initAdapter(result.data)
                }
                is AsyncResult.Error -> {
                    Timber.e(result.exception)
                }
            }
        })

        vmItem.resultItem.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is AsyncResult.Success -> {
                    rcvItem.post {
                        adapter.notify(result.data.toPresentation())
                    }
                }
            }
        })
    }

    private fun initAdapter(ids: List<Int>) {
        adapter = StoryAdapter(ids) {
            vmItem.fetchItem(it)
        }

        rcvItem.adapter = adapter
    }

    private fun fetchData() {
        vmItem.fetchItems(type)
    }
}
