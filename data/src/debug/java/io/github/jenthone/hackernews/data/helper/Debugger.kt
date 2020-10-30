package io.github.jenthone.hackernews.data.helper

import android.app.Application
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

fun Application.attachToDebugger() {
    Stetho.initializeWithDefaults(this)
    Timber.plant(Timber.DebugTree())
}

fun OkHttpClient.Builder.attachToDebugger() {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    addInterceptor(
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    )
    addNetworkInterceptor(StethoInterceptor())
}
