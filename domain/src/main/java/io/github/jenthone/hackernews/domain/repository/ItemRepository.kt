package io.github.jenthone.hackernews.domain.repository

import io.github.jenthone.hackernews.domain.entity.Item

interface ItemRepository {
    suspend fun fetchItem(id: Int): Result<Item>

    suspend fun fetchOfflineItem(id: Int): Result<Item>
}
