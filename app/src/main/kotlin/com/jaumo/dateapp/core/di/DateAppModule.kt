package com.jaumo.dateapp.core.di

import com.jaumo.dateapp.BuildConfig
import com.jaumo.dateapp.core.analytics.Analytics
import com.jaumo.dateapp.core.analytics.AnalyticsImpl
import com.jaumo.dateapp.features.zapping.data.remote.UserApi
import com.jaumo.dateapp.features.zapping.data.repository.UserRepositoryImpl
import com.jaumo.dateapp.features.zapping.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

/***
 * Date App module.
 * This object is used for DI.
 */
@Module
@InstallIn(SingletonComponent::class)
object DateAppModule {
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    fun provideAnalytics(): Analytics = AnalyticsImpl()

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi,
        analytics: Analytics,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): UserRepository = UserRepositoryImpl(
        userApi, analytics, defaultDispatcher
    )

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideUserApi(okHttpClient: OkHttpClient): UserApi {
        return Retrofit.Builder()
            .baseUrl(UserApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher