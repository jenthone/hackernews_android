package io.github.jenthone.hackernews.helper.glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import io.github.jenthone.hackernews.domain.helper.exception.DataNotFoundException
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.java.KoinJavaComponent.inject
import java.io.InputStream

/**
 * A data fetcher for load thumbnail image from the given url.
 */
class ThumbnailOfUrlDataFetcher(
    private val model: String
) : DataFetcher<InputStream> {

    private val okHttpClient: OkHttpClient by inject(OkHttpClient::class.java)

    override fun getDataClass(): Class<InputStream> = InputStream::class.java

    override fun cleanup() {
        // TODO: Release/clean all network request.
    }

    override fun getDataSource(): DataSource = DataSource.REMOTE

    override fun cancel() {
        // TODO: Cancel network request.
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        val request = Request.Builder()
            .url(model)
            .build()
        val content = try {
            okHttpClient.newCall(request).execute().body?.string()
                ?: throw DataNotFoundException()
        } catch (e: Exception) {
            callback.onLoadFailed(e)
            return
        }

        // TODO: Parse url thumbnail by using jsoup.

        // TODO: Load input stream from url-thumbnail.
    }
}
