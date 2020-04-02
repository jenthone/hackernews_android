package io.github.jenthone.hackernews.helper.glide

import android.webkit.URLUtil
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoader.LoadData
import com.bumptech.glide.signature.ObjectKey
import java.io.InputStream

/**
 * A custom model loader to load a thumbnail image from given url.
 */
class ThumbnailOfUrlModelLoader : ModelLoader<String, InputStream> {
    override fun buildLoadData(
        model: String,
        width: Int,
        height: Int,
        options: Options
    ): LoadData<InputStream>? =
        LoadData(ObjectKey(model), ThumbnailOfUrlDataFetcher(model))

    override fun handles(model: String): Boolean = URLUtil.isValidUrl(model)
}
