package io.github.jenthone.hackernews.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.domain.helper.AsyncResult
import io.github.jenthone.hackernews.domain.repository.ItemRepository
import io.github.jenthone.hackernews.domain.repository.StoryRepository
import kotlinx.coroutines.launch

class ItemViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {
    private val resultStories = MutableLiveData<AsyncResult<List<Int>>>(AsyncResult.Initialize)
    val liveResultStories: LiveData<AsyncResult<List<Int>>> = resultStories
    private val resultItem = MutableLiveData<AsyncResult<Item>>(AsyncResult.Initialize)
    val liveResultItem: LiveData<AsyncResult<Item>> = resultItem

    fun fetchItems(type: StoryType) {
        viewModelScope.launch {
            resultStories.value = AsyncResult.Loading
            resultStories.postValue(storyRepository.fetchStories(type))
        }
    }

    fun fetchItem(id: Int) {
        viewModelScope.launch {
            resultItem.postValue(itemRepository.fetchOfflineItem(id))
            resultItem.postValue(itemRepository.fetchItem(id))
        }
    }
}
