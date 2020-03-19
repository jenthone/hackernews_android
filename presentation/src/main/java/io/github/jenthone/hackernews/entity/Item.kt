package io.github.jenthone.hackernews.entity

data class Item(
    val id: Int,
    var deleted: Boolean?,
    var type: String?,
    var `by`: String?,
    var time: Int?,
    var text: String?,
    var dead: Boolean?,
    var parent: Int?,
    var poll: Int?,
    var kids: List<Int>?,
    var url: String?,
    var score: Int?,
    var title: String?,
    var parts: List<Int>?,
    var descendants: Int?
)