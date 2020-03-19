package io.github.jenthone.hackernews.domain.entity

data class Item(
    val id: Int,
    val deleted: Boolean?,
    val type: String?,
    val `by`: String?,
    val time: Int?,
    val text: String?,
    val dead: Boolean?,
    val parent: Int?,
    val poll: Int?,
    val kids: List<Int>?,
    val url: String?,
    val score: Int?,
    val title: String?,
    val parts: List<Int>?,
    val descendants: Int?
)