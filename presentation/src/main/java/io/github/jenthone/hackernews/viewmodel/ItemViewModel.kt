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
    private val modifiableResultStories = MutableLiveData<AsyncResult<List<Int>>>(
        AsyncResult.Initialize
    )
    val resultStories: LiveData<AsyncResult<List<Int>>> = modifiableResultStories
    private val modifiableResultItem = MutableLiveData<AsyncResult<Item>>(AsyncResult.Initialize)
    val resultItem: LiveData<AsyncResult<Item>> = modifiableResultItem

    fun fetchItems(type: StoryType) {
        viewModelScope.launch {
            modifiableResultStories.value = AsyncResult.Loading
            modifiableResultStories.postValue(storyRepository.fetchStories(type))
        }
    }

    fun fetchItem(id: Int) {
        viewModelScope.launch {
            modifiableResultItem.postValue(itemRepository.fetchOfflineItem(id))
            modifiableResultItem.postValue(itemRepository.fetchItem(id))
        }
    }
}
