package io.github.jenthone.hackernews.ui.story

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.domain.usecase.GetItemUseCase
import io.github.jenthone.hackernews.domain.usecase.GetStoriesUseCase
import kotlinx.coroutines.launch

class StoryViewModel @ViewModelInject constructor(
    private val getItemUseCase: GetItemUseCase,
    private val getStoriesUseCase: GetStoriesUseCase
) : ViewModel() {
    private val modifiableStories = MutableLiveData<Result<List<Int>>>()
    val stories: LiveData<Result<List<Int>>> = modifiableStories
    private val modifiableItem = MutableLiveData<Result<Item>>()
    val item: LiveData<Result<Item>> = modifiableItem

    fun fetchItems(type: StoryType) {
        viewModelScope.launch {
            modifiableStories.postValue(getStoriesUseCase.execute(type))
        }
    }

    fun fetchItem(id: Int) {
        viewModelScope.launch {
            modifiableItem.postValue(getItemUseCase.execute(id))
        }
    }
}
