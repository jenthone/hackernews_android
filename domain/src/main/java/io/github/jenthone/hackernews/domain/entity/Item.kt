package io.github.jenthone.hackernews.domain.entity

data class Item(
    val id: Int,
    val type: String?,
    val `by`: String?,
    val time: Int?,
    val text: String?,
    val url: String?,
    val score: Int?,
    val title: String?,
    val descendants: Int?
)
