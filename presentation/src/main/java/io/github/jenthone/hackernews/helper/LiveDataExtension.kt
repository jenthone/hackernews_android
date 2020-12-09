package io.github.jenthone.hackernews.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, action: (T) -> Unit) {
    observe(owner, { it?.let(action) })
}
