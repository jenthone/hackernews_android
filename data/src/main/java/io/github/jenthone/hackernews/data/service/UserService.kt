package io.github.jenthone.hackernews.data.service

import io.github.jenthone.hackernews.data.entity.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("user/{id}.json")
    suspend fun fetchUser(@Path("id") id: String): UserResponse
}
