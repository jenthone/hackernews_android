package io.github.jenthone.hackernews.data.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.github.jenthone.hackernews.data.BuildConfig
import io.github.jenthone.hackernews.data.Const
import io.github.jenthone.hackernews.data.helper.JsonHelper
import io.github.jenthone.hackernews.data.helper.attachToDebugger
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.attachToDebugger()
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = JsonHelper.moshi

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Const.BASE_URL)
        .client(okHttpClient)
        .build()
}
