package io.github.jenthone.hackernews.domain.usecase

import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.domain.repository.ItemRepository

class GetItemUseCase(private val itemRepository: ItemRepository) {
    suspend fun execute(id: Int): Result<Item> {
        val result = itemRepository.fetchItem(id)
        if (result.isSuccess) {
            return result
        }
        return itemRepository.fetchItem(id)
    }
}
