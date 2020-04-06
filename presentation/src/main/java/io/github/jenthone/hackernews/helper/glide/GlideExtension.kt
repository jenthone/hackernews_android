package io.github.jenthone.hackernews.helper.glide

import android.widget.ImageView

fun ImageView.load(request: Any, builder: GlideRequest<*>.() -> Unit = {}) {
    GlideApp.with(this)
        .load(request)
        .also(builder)
        .into(this)
}

fun ImageView.loadThumbnail(url: String) {
    load(ThumbnailOfUrlRequest(url)) {
        centerCrop()
    }
}
