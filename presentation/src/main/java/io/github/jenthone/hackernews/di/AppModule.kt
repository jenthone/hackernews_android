package io.github.jenthone.hackernews.di

import io.github.jenthone.hackernews.app
import io.github.jenthone.hackernews.data.di.dataModule
import io.github.jenthone.hackernews.viewmodel.ItemViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { androidContext().app }
}

val viewModelModule = module {
    viewModel { ItemViewModel(get(), get()) }
}

val diModules = listOf(
    dataModule,
    appModule,
    viewModelModule
)
