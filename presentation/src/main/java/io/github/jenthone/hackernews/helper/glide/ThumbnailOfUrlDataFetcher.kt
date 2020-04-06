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
    private val model: ThumbnailUrlRequest
) : DataFetcher<InputStream> {

    private val okHttpClient: OkHttpClient by inject(OkHttpClient::class.java)

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
        val content = loadHtmlContentFromUrl(url)
        val urlThumbnail = parseUrlThumbnailFromHtmlContent(content) ?: getThumbnailUrlFromHost()
        return openInputStreamForUrl(urlThumbnail)
    }

    private fun openInputStreamForUrl(url: String): InputStream {
        val requestUrlThumbnail = Request.Builder()
            .url(url)
            .build()
        return okHttpClient.newCall(requestUrlThumbnail).execute().body?.byteStream()
            ?: throw DataNotFoundException()
    }

    private fun loadHtmlContentFromUrl(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()
        return okHttpClient.newCall(request).execute().body?.string()
            ?: throw DataNotFoundException()
    }

    /**
     * Parses url thumbnail by select meta attributes of html `og` tag.
     */
    private fun parseUrlThumbnailFromHtmlContent(htmlContent: String): String? {
        val doc = Jsoup.parse(htmlContent)
        val metaOgImage = doc.select("meta[property=og:image]")
        return metaOgImage?.attr("content")?.takeIf(String::isNotBlank)
    }

    private fun getThumbnailUrlFromHost(): String {
        val host = Uri.parse(model.url).host
        return "https://api.faviconkit.com/${host}/128"
    }
}
