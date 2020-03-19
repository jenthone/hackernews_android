package io.github.jenthone.hackernews.data.helper

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object JsonHelper {
    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun <T> fromJson(json: String, clazz: Class<T>): T? {
        val adapter = moshi.adapter<T>(clazz)
        return adapter.fromJson(json)
    }

    fun <T> toJson(value: T, clazz: Class<T>): String {
        val adapter = moshi.adapter<T>(clazz)
        return adapter.toJson(value)
    }
}