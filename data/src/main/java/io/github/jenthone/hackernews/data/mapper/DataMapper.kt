package io.github.jenthone.hackernews.data.mapper

import io.github.jenthone.hackernews.data.entity.ItemResponse
import io.github.jenthone.hackernews.data.local.ItemEntity
import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.domain.entity.StoryType

fun StoryType.toData() = name.toLowerCase()

fun ItemResponse.toDomain() = Item(
    id,
    deleted,
    type,
    by,
    time,
    text,
    dead,
    parent,
    poll,
    kids,
    url,
    score,
    title,
    parts,
    descendants
)

fun ItemEntity.toDomain() = Item(
    id,
    null,
    type,
    by,
    time,
    text,
    null,
    null,
    null,
    null,
    url,
    null,
    title,
    null,
    null
)

fun Item.toLocal() = ItemEntity(
    id,
    type,
    by,
    time,
    text,
    url,
    title
)
