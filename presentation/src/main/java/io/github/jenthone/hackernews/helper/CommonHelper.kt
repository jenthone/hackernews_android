package io.github.jenthone.hackernews.helper

import org.ocpsoft.prettytime.PrettyTime
import java.util.*

fun Int.timeFormat(): String {
    val t = PrettyTime()
    return t.format(Date(this * 1000L))
}