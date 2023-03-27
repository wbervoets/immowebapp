package be.wimbervoets.immowebservice.data.service.di.retrofit

import be.wimbervoets.immowebservice.data.service.di.ListingAPI
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {
    @Reusable
    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient,
        httpConverterFactory: Converter.Factory,
        @ListingAPI listingAPIBaseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(httpConverterFactory)
            .baseUrl(listingAPIBaseUrl)
            .client(okHttpClient)
            .build()
    }
}
