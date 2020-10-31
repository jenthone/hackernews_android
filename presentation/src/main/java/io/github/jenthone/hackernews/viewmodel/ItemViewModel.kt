package io.github.jenthone.hackernews.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.domain.usecase.GetItemUseCase
import io.github.jenthone.hackernews.domain.usecase.GetStoriesUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ItemViewModel @ViewModelInject constructor(
    private val getItemUseCase: GetItemUseCase,
    private val getStoriesUseCase: GetStoriesUseCase
) : ViewModel() {
    private val modifiableResultStories = MutableLiveData<Result<List<Int>>>()
    val resultStories: LiveData<Result<List<Int>>> = modifiableResultStories
    private val modifiableResultItem = MutableLiveData<Result<Item>>()
    val resultItem: LiveData<Result<Item>> = modifiableResultItem

    fun fetchItems(type: StoryType) {
        viewModelScope.launch {
            modifiableResultStories.postValue(getStoriesUseCase.execute(type))
        }
    }

    fun fetchItem(id: Int) {
        viewModelScope.launch {
            getItemUseCase.execute(id).collect {
                modifiableResultItem.postValue(it)
            }
        }
    }
}
