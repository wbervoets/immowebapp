package be.wimbervoets.immowebservice.data.service.di.json

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import timber.log.Timber

@InstallIn(SingletonComponent::class)
@Module
object JsonModule {
    @Provides
    @Reusable
    fun json(): Json {
        Timber.d("Creating kotlinx.serialization.json.Json")
        return Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}
