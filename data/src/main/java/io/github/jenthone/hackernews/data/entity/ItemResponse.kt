package io.github.jenthone.hackernews.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "type")
    val type: String?,
    @Json(name = "by")
    val `by`: String?,
    @Json(name = "time")
    val time: Int?,
    @Json(name = "text")
    val text: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "score")
    val score: Int?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "descendants")
    val descendants: Int?
)
