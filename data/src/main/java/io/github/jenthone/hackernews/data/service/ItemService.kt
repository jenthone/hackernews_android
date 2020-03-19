package io.github.jenthone.hackernews.data.service

import io.github.jenthone.hackernews.data.entity.ItemResponse
import retrofit2.http.*

interface ItemService {
    @GET("item/{id}.json")
    suspend fun fetchItem(@Path("id") id: Int): ItemResponse
}