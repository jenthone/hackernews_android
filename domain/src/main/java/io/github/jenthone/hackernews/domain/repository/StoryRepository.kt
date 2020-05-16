package io.github.jenthone.hackernews.domain.repository

import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.domain.helper.AsyncResult

interface StoryRepository {
    suspend fun fetchStories(type: StoryType): AsyncResult<List<Int>>
}
