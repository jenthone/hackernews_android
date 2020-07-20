package io.github.jenthone.hackernews

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.jenthone.hackernews.data.helper.attachToDebugger

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            attachToDebugger()
        }
    }
}
