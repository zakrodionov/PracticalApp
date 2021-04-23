package com.zakrodionov.practicalapp.app.di.initializer

import com.squareup.moshi.Moshi
import com.zakrodionov.common.extensions.isDebug
import com.zakrodionov.common.utils.net.ConnectionService
import com.zakrodionov.common.utils.net.ConnectionServiceImpl
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.FlipperInitializer
import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProvider
import com.zakrodionov.practicalapp.app.environment.interceptors.MainHeaderInterceptor
import com.zakrodionov.practicalapp.app.environment.interceptors.MainTokenAuthenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetInitializer : Initializer {

    private const val TIMEOUT = 30L
    private const val MAX_REQUESTS_PER_HOST = 10

    // For common api
    private const val NAME_MAIN_OK_HTTP = "mainOkHttp"
    const val NAME_MAIN_RETROFIT = "mainRetrofit"

    // For refresh token
    // private const val NAME_OAUTH_OK_HTTP = "oauthOkHttp"
    // const val NAME_OAUTH_RETROFIT = "oauthRetrofit"

    override fun initialize(appModule: Module) {
        appModule.apply {
            single { buildMoshi() }

            single<ConnectionService> { ConnectionServiceImpl(get(), get<DispatchersProvider>().default) }

            single { MainHeaderInterceptor(get()) }
            single { MainTokenAuthenticator() }

            single(named(NAME_MAIN_OK_HTTP)) { buildMainOkHttp(get(), get()) }
            single(named(NAME_MAIN_RETROFIT)) { buildMainRetrofit(get()) }
        }
    }

    private fun buildMoshi(): Moshi = Moshi.Builder().build()

    private fun buildMainOkHttp(
        mainHeaderInterceptor: MainHeaderInterceptor,
        mainTokenAuthenticator: MainTokenAuthenticator
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

    private fun Scope.buildMainRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(get(named(NAME_MAIN_OK_HTTP)))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}
