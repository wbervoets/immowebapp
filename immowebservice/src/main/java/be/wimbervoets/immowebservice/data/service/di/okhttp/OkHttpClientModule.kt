package be.wimbervoets.immowebservice.data.service.di.okhttp

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
object OkHttpClientModule {

    // OKHttp keeps state (a connectionpool and threadpools) per client, recommended to be singleton
    // https://square.github.io/okhttp/3.x/okhttp/okhttp3/OkHttpClient.html
    private const val MAX_IDLE_CONNNECTIONS = 20
    private const val KEEP_ALIVE_DURATION = 60L
    private const val TWENTY_FIVE_MB = 25 * 1024 * 1024

    @Provides
    @Reusable
    fun providesOkHttpCache(@ApplicationContext context: Context): Cache {
        Timber.d("Creating OkHttp Cache (25MB)")
        return Cache(context.cacheDir.resolve( "http_cache"), TWENTY_FIVE_MB.toLong())
    }

    @Provides
    @Reusable
    fun providesConnectionPool(): ConnectionPool {
        Timber.d("Creating OkHttp Connection pool")
        return ConnectionPool(MAX_IDLE_CONNNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.SECONDS)
    }

    @Provides
    @Reusable
    fun okHttpClient(
        cache: Cache,
        connectionPool: ConnectionPool
    ): OkHttpClient {
        Timber.d("Creating okHttpClient")
        return OkHttpClient.Builder()
            .connectionPool(connectionPool)
            .cache(cache)
            .build()
    }
}
