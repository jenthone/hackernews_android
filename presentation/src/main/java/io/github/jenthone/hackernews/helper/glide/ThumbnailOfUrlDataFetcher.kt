package io.github.jenthone.hackernews.helper.glide

import android.net.Uri
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import io.github.jenthone.hackernews.domain.helper.exception.DataNotFoundException
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
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

        val doc = Jsoup.parse(content)
        val metaOgImage = doc.select("meta[property=og:image]")
        val urlThumbnailFromOgTag = metaOgImage?.attr("content")
        val urlThumbnail = urlThumbnailFromOgTag ?: getThumbnailUrlOfHost()

        val requestUrlThumbnail = Request.Builder()
            .url(urlThumbnail)
            .build()
        val inputStream = try {
            okHttpClient.newCall(requestUrlThumbnail)
                .execute()
                .body?.byteStream()
                ?: throw DataNotFoundException()
        } catch (e: Exception) {
            callback.onLoadFailed(e)
            return
        }
        callback.onDataReady(inputStream)
    }

    private fun getThumbnailUrlOfHost(): String {
        val host = Uri.parse(model).host
        return "https://api.faviconkit.com/${host}/128"
    }
}
