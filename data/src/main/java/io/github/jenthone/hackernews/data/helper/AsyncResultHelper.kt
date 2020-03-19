package io.github.jenthone.hackernews.data.helper

import io.github.jenthone.hackernews.domain.helper.AsyncResult
import timber.log.Timber

inline fun <T> safeResult(block: () -> T): AsyncResult<T> {
    return safeResult(block) { it }
}

inline fun <T, E> safeResult(block: () -> T, map: (T) -> E): AsyncResult<E> {
    return try {
        val data = block()
        AsyncResult.Success(map(data))
    } catch (e: Throwable) {
        Timber.e(e)
        AsyncResult.Error(e)
    }
}