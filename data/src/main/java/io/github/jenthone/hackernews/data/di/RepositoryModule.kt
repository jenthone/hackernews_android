package io.github.jenthone.hackernews.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.github.jenthone.hackernews.data.repository.ItemRepositoryImpl
import io.github.jenthone.hackernews.data.repository.StoryRepositoryImpl
import io.github.jenthone.hackernews.domain.repository.ItemRepository
import io.github.jenthone.hackernews.domain.repository.StoryRepository

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun provideItemRepository(
        itemRepositoryImpl: ItemRepositoryImpl
    ): ItemRepository

    @Binds
    @ActivityRetainedScoped
    abstract fun provideStoryRepository(
        storyRepositoryImpl: StoryRepositoryImpl
    ): StoryRepository
}
