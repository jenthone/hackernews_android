package io.github.jenthone.hackernews.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "delay")
    val delay: Int?,
    @Json(name = "created")
    val created: Int,
    @Json(name = "karma")
    val karma: Int,
    @Json(name = "about")
    val about: String?,
    @Json(name = "submitted")
    val submitted: List<Int>?
)
