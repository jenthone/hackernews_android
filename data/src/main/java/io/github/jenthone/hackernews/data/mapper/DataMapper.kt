package io.github.jenthone.hackernews.data.mapper

import io.github.jenthone.hackernews.data.entity.ItemResponse
import io.github.jenthone.hackernews.data.local.ItemEntity
import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.domain.entity.StoryType

fun StoryType.toData() = name.toLowerCase()

fun ItemResponse.toDomain() = Item(id, type, by, time, text, url, score, title, descendants)

fun ItemEntity.toDomain() = Item(id, type, by, time, text, url, score, title, descendants)

fun Item.toLocal() = ItemEntity(id, type, by, time, text, url, score, title, descendants)
