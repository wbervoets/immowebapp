package be.wimbervoets.immowebapp

import android.app.Application
import be.wimbervoets.immowebapp.di.CoilModule
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ImmowebApp: Application(), ImageLoaderFactory {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun newImageLoader(): ImageLoader {
        return CoilModule.coilImageLoader(applicationContext, okHttpClient)
    }
}