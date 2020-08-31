package io.github.jenthone.hackernews.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import org.ocpsoft.prettytime.PrettyTime
import java.util.Date

fun Int.timeFormat(): String {
    val t = PrettyTime()
    return t.format(Date(this * 1000L))
}

fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, action: (T) -> Unit) {
    observe(owner, { it?.let(action) })
}
