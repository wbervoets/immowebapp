package be.wimbervoets.immowebservice.data.service.di

import be.wimbervoets.immowebservice.data.service.ListingService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@InstallIn(ActivityRetainedComponent::class)
@Module
object ListingServiceModule {

    @Provides
    @Reusable
    fun openWeatherService(retrofit: Retrofit): ListingService {
        return retrofit.create(ListingService::class.java)
    }
}