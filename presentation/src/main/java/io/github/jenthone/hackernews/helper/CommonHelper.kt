package io.github.jenthone.hackernews.helper

import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import coil.api.load
import io.github.jenthone.hackernews.data.Const
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

fun Int.timeFormat(): String {
    val t = PrettyTime()
    return t.format(Date(this * 1000L))
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, action: (T) -> Unit) {
    observe(owner, Observer { action(it) })
}

fun ImageView.loadIcon(url: String?) {
    val url = url ?: Const.BASE_WEB_URL
    val host = Uri.parse(url).host
    load("https://api.faviconkit.com/${host}/128")
}
