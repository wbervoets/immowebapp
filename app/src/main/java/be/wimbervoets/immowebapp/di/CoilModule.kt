package be.wimbervoets.immowebapp.di

import android.content.Context
import be.wimbervoets.immowebapp.BuildConfig
import coil.ImageLoader
import coil.disk.DiskCache
import coil.util.DebugLogger
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@InstallIn(SingletonComponent::class)
@Module
object CoilModule {

    @Provides
    @Reusable
    fun coilImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        val imageLoaderBuilder = ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .build()
            }
        if (BuildConfig.DEBUG) {
            imageLoaderBuilder.logger(DebugLogger())
        }
        return imageLoaderBuilder.build()
    }
}
