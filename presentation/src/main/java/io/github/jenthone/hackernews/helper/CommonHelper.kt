package io.github.jenthone.hackernews.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

fun Int.timeFormat(): String {
    val t = PrettyTime()
    return t.format(Date(this * 1000L))
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, action: (T) -> Unit) {
    observe(owner, Observer { action(it) })
}

fun <T> MutableLiveData<T>.toImmutable(): LiveData<T> {
    return this
}