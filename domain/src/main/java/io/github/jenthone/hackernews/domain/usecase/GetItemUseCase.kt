package io.github.jenthone.hackernews.domain.usecase

import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.domain.helper.AsyncResult
import io.github.jenthone.hackernews.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetItemUseCase(private val itemRepository: ItemRepository) {
    suspend fun execute(id: Int): Flow<AsyncResult<Item>> {
        return flow {
            emit(itemRepository.fetchOfflineItem(id))
            emit(itemRepository.fetchItem(id))
        }
    }
}
