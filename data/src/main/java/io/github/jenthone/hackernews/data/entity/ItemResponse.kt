package io.github.jenthone.hackernews.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "deleted")
    val deleted: Boolean?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "by")
    val `by`: String?,
    @Json(name = "time")
    val time: Int?,
    @Json(name = "text")
    val text: String?,
    @Json(name = "dead")
    val dead: Boolean?,
    @Json(name = "parent")
    val parent: Int?,
    @Json(name = "poll")
    val poll: Int?,
    @Json(name = "kids")
    val kids: List<Int>?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "score")
    val score: Int?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "parts")
    val parts: List<Int>?,
    @Json(name = "descendants")
    val descendants: Int?
)