package io.github.jenthone.hackernews.mapper

import io.github.jenthone.hackernews.domain.entity.Item

fun Item.toPresentation() = io.github.jenthone.hackernews.entity.Item(
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