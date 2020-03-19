package io.github.jenthone.hackernews.data.repository

import io.github.jenthone.hackernews.data.helper.safeResult
import io.github.jenthone.hackernews.data.mapper.toData
import io.github.jenthone.hackernews.data.service.StoryService
import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.domain.helper.AsyncResult
import io.github.jenthone.hackernews.domain.repository.StoryRepository

class StoryRepositoryImpl(private val service: StoryService) : StoryRepository {
    override suspend fun fetchStories(type: StoryType): AsyncResult<List<Int>> {
        return safeResult { service.fetchStories(type.toData()).take(30) }
    }
}