package io.github.jenthone.hackernews.data.helper.glide

import android.webkit.URLUtil
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader.LoadData
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.signature.ObjectKey
import java.io.InputStream

/**
 * A custom model loader to load a thumbnail image from given url.
 */
class ThumbnailOfUrlModelLoader : ModelLoader<ThumbnailOfUrlRequest, InputStream> {
    override fun buildLoadData(
        model: ThumbnailOfUrlRequest,
        width: Int,
        height: Int,
        options: Options
    ): LoadData<InputStream>? =
        LoadData(ObjectKey(model), ThumbnailOfUrlDataFetcher(model))

    override fun handles(model: ThumbnailOfUrlRequest): Boolean = URLUtil.isValidUrl(model.url)
}
