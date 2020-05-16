package io.github.jenthone.hackernews.data.helper

import android.app.Application
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import tech.linjiang.pandora.Pandora
import timber.log.Timber

fun Application.attachToDebugger() {
    Stetho.initializeWithDefaults(this)
    Timber.plant(Timber.DebugTree())
}

fun Application.openDebugger() {
    Pandora.get().open()
}

fun OkHttpClient.Builder.attachToDebugger() {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    addInterceptor(httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    })
    addNetworkInterceptor(StethoInterceptor())
    addInterceptor(Pandora.get().interceptor)
}
