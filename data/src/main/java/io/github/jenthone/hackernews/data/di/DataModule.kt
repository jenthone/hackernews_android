package io.github.jenthone.hackernews.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jenthone.hackernews.data.local.AppDatabase
import io.github.jenthone.hackernews.data.local.ItemDao
import io.github.jenthone.hackernews.data.service.ItemService
import io.github.jenthone.hackernews.data.service.StoryService
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

    private inline fun <reified T> createApiService(retrofit: Retrofit): T {
        return retrofit.create(T::class.java)
    }
}
