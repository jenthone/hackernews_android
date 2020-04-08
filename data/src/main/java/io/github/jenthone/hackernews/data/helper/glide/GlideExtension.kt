package io.github.jenthone.hackernews.data.helper.glide

import android.widget.ImageView
import androidx.annotation.DrawableRes

fun ImageView.load(request: Any, builder: GlideRequest<*>.() -> Unit = {}) {
    GlideApp.with(this)
        .load(request)
        .also(builder)
        .into(this)
}

fun ImageView.loadThumbnail(url: String, @DrawableRes placeHolderDrawableResId: Int) {
    load(ThumbnailOfUrlRequest(url)) {
        placeholder(placeHolderDrawableResId)
        centerInside()
    }
}
