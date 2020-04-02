package io.github.jenthone.hackernews.helper.glide

import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.io.InputStream

class ThumbnailOfUrlModelLoaderFactory : ModelLoaderFactory<ThumbnailUrlRequest, InputStream> {
    override fun build(
        multiFactory: MultiModelLoaderFactory
    ): ModelLoader<ThumbnailUrlRequest, InputStream> = ThumbnailOfUrlModelLoader()

    override fun teardown() = Unit
}
