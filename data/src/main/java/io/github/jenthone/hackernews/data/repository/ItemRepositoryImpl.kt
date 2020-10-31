package io.github.jenthone.hackernews.data.repository

import io.github.jenthone.hackernews.data.helper.safeResult
import io.github.jenthone.hackernews.data.local.ItemDao
import io.github.jenthone.hackernews.data.mapper.toDomain
import io.github.jenthone.hackernews.data.mapper.toLocal
import io.github.jenthone.hackernews.data.service.ItemService
import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.domain.helper.exception.DataNotFoundException
import io.github.jenthone.hackernews.domain.repository.ItemRepository
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val service: ItemService,
    private val itemDao: ItemDao
) :
    ItemRepository {
    override suspend fun fetchOfflineItem(id: Int): Result<Item> {
        val localEntity = itemDao.fetch(id)
        if (localEntity != null) {
            return Result.success(localEntity.toDomain())
        }
        return Result.failure(DataNotFoundException())
    }

    override suspend fun fetchItem(id: Int): Result<Item> {
        return safeResult {
            val item = service.fetchItem(id).toDomain()
            itemDao.insert(
                item.toLocal()
            )
            return@safeResult item
        }
    }
}
