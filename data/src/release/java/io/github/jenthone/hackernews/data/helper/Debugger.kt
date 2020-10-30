package io.github.jenthone.hackernews.data.helper

import android.app.Application
import okhttp3.OkHttpClient

fun Application.attachToDebugger() = Unit

fun OkHttpClient.Builder.attachToDebugger() = Unit
