package io.github.jenthone.hackernews.domain.helper

sealed class AsyncResult<out T> {
    data class Success<T>(val data: T) : AsyncResult<T>()
    data class Error(val exception: Throwable) : AsyncResult<Nothing>()
    object Loading : AsyncResult<Nothing>()
    object Initialize : AsyncResult<Nothing>()
}