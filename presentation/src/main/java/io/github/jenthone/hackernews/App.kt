package io.github.jenthone.hackernews

import android.app.Application
import android.content.Context
import io.github.jenthone.hackernews.data.helper.attachToDebugger
import io.github.jenthone.hackernews.di.diModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            attachToDebugger()
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(diModules)
        }
    }
}

val Context.app: App
    get() = applicationContext as App
