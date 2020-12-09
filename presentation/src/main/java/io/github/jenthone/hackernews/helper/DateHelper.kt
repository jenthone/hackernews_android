package io.github.jenthone.hackernews.helper

import org.ocpsoft.prettytime.PrettyTime
import java.util.Date

object DateHelper {
    fun formatTime(time: Int): String {
        val t = PrettyTime()
        return t.format(Date(time * 1000L))
    }
}
