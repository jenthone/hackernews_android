package io.github.jenthone.hackernews.domain.repository

import io.github.jenthone.hackernews.domain.entity.StoryType

interface StoryRepository {
    suspend fun fetchStories(type: StoryType): Result<List<Int>>
}
