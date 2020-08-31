package io.github.jenthone.hackernews.data.service

import io.github.jenthone.hackernews.data.entity.ItemResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemService {
    @GET("item/{id}.json")
    suspend fun fetchItem(@Path("id") id: Int): ItemResponse
}
