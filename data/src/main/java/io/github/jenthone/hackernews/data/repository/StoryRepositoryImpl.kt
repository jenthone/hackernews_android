package io.github.jenthone.hackernews.data.repository

import io.github.jenthone.hackernews.data.helper.safeResult
import io.github.jenthone.hackernews.data.mapper.toData
import io.github.jenthone.hackernews.data.service.StoryService
import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.domain.repository.StoryRepository
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val service: StoryService
) : StoryRepository {
    override suspend fun fetchStories(type: StoryType): Result<List<Int>> {
        return safeResult { service.fetchStories(type.toData()).take(30) }
    }
}
