package io.github.jenthone.hackernews.data.helper.glide

import android.net.Uri
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import java.io.InputStream
import java.net.URL
import org.jsoup.Jsoup

/**
 * A data fetcher for load thumbnail image from the given url.
 */
class ThumbnailOfUrlDataFetcher(
    private val model: ThumbnailOfUrlRequest
) : DataFetcher<InputStream> {

    override fun getDataClass(): Class<InputStream> = InputStream::class.java

    override fun cleanup() = Unit

    override fun getDataSource(): DataSource = DataSource.REMOTE

    override fun cancel() = Unit

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) =
        try {
            val inputStream = loadDataInternal()
            callback.onDataReady(inputStream)
        } catch (e: Exception) {
            callback.onLoadFailed(e)
        }

    private fun loadDataInternal(): InputStream {
        val url = model.url
        val urlThumbnail = parseUrlThumbnailFromHtmlContent(url) ?: defaultThumbnailOf(url)
        return openInputStreamForUrl(urlThumbnail)
    }

    private fun openInputStreamForUrl(url: String): InputStream {
        return URL(url).openStream()
    }

    /**
     * Parses url thumbnail by select meta attributes of html `og` tag.
     */
    private fun parseUrlThumbnailFromHtmlContent(url: String): String? {
        val doc = Jsoup.connect(url).get()
        val metaOgImage = doc?.select("meta[property=og:image]")
        return metaOgImage?.attr("content")?.takeIf(String::isNotBlank)
    }

    private fun defaultThumbnailOf(url: String): String {
        val host = Uri.parse(url).host
        return "https://api.faviconkit.com/$host/128"
    }
}
