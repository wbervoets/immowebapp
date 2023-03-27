package be.wimbervoets.immowebservice.data.service.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
annotation class ListingAPI

@InstallIn(SingletonComponent::class)
@Module
object ServerURLModule {
    @Reusable
    @Provides
    @ListingAPI
    fun listingServiceBaseURL(): String {
        return "https://gsl-apps-technical-test.dignp.com"
    }
}
