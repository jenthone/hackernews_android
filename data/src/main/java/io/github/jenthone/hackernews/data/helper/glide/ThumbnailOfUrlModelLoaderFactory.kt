package io.github.jenthone.hackernews.data.helper.glide

import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.io.InputStream

class ThumbnailOfUrlModelLoaderFactory : ModelLoaderFactory<ThumbnailOfUrlRequest, InputStream> {
    override fun build(
        multiFactory: MultiModelLoaderFactory
    ): ModelLoader<ThumbnailOfUrlRequest, InputStream> = ThumbnailOfUrlModelLoader()

    override fun teardown() = Unit
}
