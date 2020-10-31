package io.github.jenthone.hackernews.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jenthone.hackernews.data.local.AppDatabase
import io.github.jenthone.hackernews.data.local.ItemDao
import io.github.jenthone.hackernews.data.repository.ItemRepositoryImpl
import io.github.jenthone.hackernews.data.repository.StoryRepositoryImpl
import io.github.jenthone.hackernews.data.service.ItemService
import io.github.jenthone.hackernews.data.service.StoryService
import io.github.jenthone.hackernews.domain.repository.ItemRepository
import io.github.jenthone.hackernews.domain.repository.StoryRepository
import io.github.jenthone.hackernews.domain.usecase.GetItemUseCase
import io.github.jenthone.hackernews.domain.usecase.GetStoriesUseCase
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideItemDao(
        appDatabase: AppDatabase
    ): ItemDao = appDatabase.itemDao()

    @Provides
    @Singleton
    fun provideItemService(
        retrofit: Retrofit
    ): ItemService = createApiService(retrofit)

    @Provides
    @Singleton
    fun provideStoryService(
        retrofit: Retrofit
    ): StoryService = createApiService(retrofit)

    @Provides
    fun provideItemRepository(
        itemService: ItemService,
        itemDao: ItemDao
    ): ItemRepository = ItemRepositoryImpl(itemService, itemDao)

    @Provides
    fun provideStoryRepository(
        storyService: StoryService
    ): StoryRepository = StoryRepositoryImpl(storyService)

    @Provides
    fun provideGetItemUseCase(itemRepository: ItemRepository): GetItemUseCase =
        GetItemUseCase(itemRepository)

    @Provides
    fun provideGetStoriesUseCase(storyRepository: StoryRepository): GetStoriesUseCase =
        GetStoriesUseCase(storyRepository)

    private inline fun <reified T> createApiService(retrofit: Retrofit): T {
        return retrofit.create(T::class.java)
    }
}
