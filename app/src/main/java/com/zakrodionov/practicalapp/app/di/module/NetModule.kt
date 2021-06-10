package com.zakrodionov.practicalapp.app.di.module

import android.content.Context
import com.squareup.moshi.Moshi
import com.zakrodionov.common.extensions.isDebug
import com.zakrodionov.common.utils.net.ConnectionService
import com.zakrodionov.common.utils.net.ConnectionServiceImpl
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.FlipperInitializer
import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProvider
import com.zakrodionov.practicalapp.app.environment.interceptors.MainHeaderInterceptor
import com.zakrodionov.practicalapp.app.environment.interceptors.MainTokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    private const val TIMEOUT = 30L
    private const val MAX_REQUESTS_PER_HOST = 10

    // For common api
    private const val NAME_MAIN_OK_HTTP = "mainOkHttp"
    const val NAME_MAIN_RETROFIT = "mainRetrofit"

    // For refresh token
    // private const val NAME_OAUTH_OK_HTTP = "oauthOkHttp"
    // const val NAME_OAUTH_RETROFIT = "oauthRetrofit"

    @Provides
    fun provideMoshi(): Moshi = buildMoshi()

    @Provides
    fun provideConnectionService(
        @ApplicationContext context: Context,
        dispatchersProvider: DispatchersProvider,
    ): ConnectionService = ConnectionServiceImpl(context, dispatchersProvider.default)

    @Provides
    fun provideOkHttp(
        mainHeaderInterceptor: MainHeaderInterceptor,
        mainTokenAuthenticator: MainTokenAuthenticator,
    ): OkHttpClient = buildMainOkHttp(mainHeaderInterceptor, mainTokenAuthenticator)

    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit = buildMainRetrofit(moshi, okHttpClient)

    private fun buildMoshi(): Moshi = Moshi.Builder().build()

    private fun buildMainOkHttp(
        mainHeaderInterceptor: MainHeaderInterceptor,
        mainTokenAuthenticator: MainTokenAuthenticator,
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(mainHeaderInterceptor)
        okHttpClientBuilder.authenticator(mainTokenAuthenticator)

        if (isDebug) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
            okHttpClientBuilder.apply {
                FlipperInitializer.interceptor?.let { addInterceptor(it) }
            }
        }

        val client = okHttpClientBuilder.build()
        client.dispatcher.maxRequestsPerHost = MAX_REQUESTS_PER_HOST

        return client
    }

    private fun buildMainRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}
