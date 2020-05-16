package io.github.jenthone.hackernews.domain.entity

data class User(
    val id: String,
    val delay: Int?,
    val created: Int,
    val karma: Int,
    val about: String?,
    val submitted: List<Int>?
)
