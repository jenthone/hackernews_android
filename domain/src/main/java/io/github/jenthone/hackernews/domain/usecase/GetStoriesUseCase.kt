package io.github.jenthone.hackernews.domain.usecase

import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.domain.helper.AsyncResult
import io.github.jenthone.hackernews.domain.repository.StoryRepository

class GetStoriesUseCase(private val storyRepository: StoryRepository) {
    suspend fun execute(storyType: StoryType): AsyncResult<List<Int>> =
        storyRepository.fetchStories(storyType)
}
