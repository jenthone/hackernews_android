package io.github.jenthone.hackernews.helper.glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import java.io.InputStream

/**
 * A data fetcher for load thumbnail image from the given url.
 */
class ThumbnailOfUrlDataFetcher(
    private val model: String
) : DataFetcher<InputStream> {
    override fun getDataClass(): Class<InputStream> = InputStream::class.java

    override fun cleanup() {
        // TODO: Release/clean all network request.
    }

    override fun getDataSource(): DataSource = DataSource.REMOTE

    override fun cancel() {
        // TODO: Cancel network request.
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        // TODO: load content of an url.

        // TODO: Parse url thumbnail by using jsoup.

        // TODO: Load input stream from url-thumbnail.
    }
}
