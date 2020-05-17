package io.github.jenthone.hackernews.data.service

import retrofit2.http.GET
import retrofit2.http.Path

interface StoryService {
    @GET("maxitem.json")
    suspend fun fetchMaxItemID(): Int

    @GET("{type}stories.json")
    suspend fun fetchStories(@Path("type") type: String): List<Int>
}
