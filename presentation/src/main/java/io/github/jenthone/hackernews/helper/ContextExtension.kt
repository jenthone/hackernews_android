package io.github.jenthone.hackernews.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import timber.log.Timber

fun Context.openLink(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    } catch (e: Exception) {
        Timber.e(e)
    }
}