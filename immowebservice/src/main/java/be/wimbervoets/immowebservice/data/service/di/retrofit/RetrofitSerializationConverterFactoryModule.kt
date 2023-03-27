package be.wimbervoets.immowebservice.data.service.di.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import timber.log.Timber

@InstallIn(SingletonComponent::class)
@Module
object RetrofitSerializationConverterFactoryModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Reusable
    @Provides
    fun jsonConverterFactory(json: Json): Converter.Factory {
        Timber.d("Creating jsonConverterFactory")
        return json.asConverterFactory("application/json".toMediaType())
    }
}
