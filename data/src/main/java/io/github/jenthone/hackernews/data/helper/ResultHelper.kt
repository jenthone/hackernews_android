package io.github.jenthone.hackernews.data.helper

import timber.log.Timber

inline fun <T> safeResult(block: () -> T): Result<T> =
    safeResult(block) { it }

inline fun <T, E> safeResult(block: () -> T, map: (T) -> E): Result<E> =
    try {
        val data = block()
        Result.success(map(data))
    } catch (e: Throwable) {
        Timber.e(e)
        Result.failure(e)
    }
