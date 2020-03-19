package io.github.jenthone.hackernews.data.di

import io.github.jenthone.hackernews.data.BuildConfig
import io.github.jenthone.hackernews.data.Const
import io.github.jenthone.hackernews.data.helper.JsonHelper
import io.github.jenthone.hackernews.data.helper.attachToDebugger
import io.github.jenthone.hackernews.data.local.AppDatabase
import io.github.jenthone.hackernews.data.repository.ItemRepositoryImpl
import io.github.jenthone.hackernews.data.repository.StoryRepositoryImpl
import io.github.jenthone.hackernews.data.service.ItemService
import io.github.jenthone.hackernews.data.service.StoryService
import io.github.jenthone.hackernews.domain.repository.ItemRepository
import io.github.jenthone.hackernews.domain.repository.StoryRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single {
        JsonHelper.moshi
    }

    single {
        val builder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.attachToDebugger()
        }

        return@single builder.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .baseUrl(Const.BASE_URL)
            .client(get())
            .build()
    }

    single {
        AppDatabase.getInstance(get())
    }

    single {
        get<AppDatabase>().itemDao()
    }

    single<ItemService> { createApiService(get()) }
    single<StoryService> { createApiService(get()) }

    factory<ItemRepository> { ItemRepositoryImpl(get(), get()) }
    factory<StoryRepository> { StoryRepositoryImpl(get()) }
}

inline fun <reified T> createApiService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}