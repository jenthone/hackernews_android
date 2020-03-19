package io.github.jenthone.hackernews.domain.repository

import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.domain.helper.AsyncResult

interface ItemRepository {
    suspend fun fetchItem(id: Int): AsyncResult<Item>

    suspend fun fetchOfflineItem(id: Int): AsyncResult<Item>
}