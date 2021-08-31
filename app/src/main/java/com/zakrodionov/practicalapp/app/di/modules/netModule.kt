package com.zakrodionov.practicalapp.app.di.modules

import com.squareup.moshi.Moshi
import com.zakrodionov.common.extensions.isDebug
import com.zakrodionov.common.network.ConnectionService
import com.zakrodionov.common.network.ConnectionServiceImpl
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.FlipperInitializer
import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProvider
import com.zakrodionov.practicalapp.app.data.network.RequestHeaderInterceptor
import com.zakrodionov.practicalapp.app.data.network.RequestTokenAuthenticator
import com.zakrodionov.practicalapp.app.data.network.buildAuthApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 30L
private const val MAX_REQUESTS_PER_HOST = 10

val netModule = module {
    single { buildMoshi() }

    single<ConnectionService> { ConnectionServiceImpl(get(), get<DispatchersProvider>().default) }

    single { RequestHeaderInterceptor(get()) }
    single { RequestTokenAuthenticator(get(), get()) }

    single { buildOkHttp(get(), get()) }
    single { buildRetrofit(get(), get()) }

    // Api for refresh tokens. Used in authenticator
    single { buildAuthApi(get()) }
}

private fun buildMoshi(): Moshi = Moshi.Builder().build()

fun basicOkHttpBuilder(): OkHttpClient.Builder {
    val okHttpClientBuilder = OkHttpClient.Builder()
    okHttpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
    okHttpClientBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS)

    if (isDebug) {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        okHttpClientBuilder.apply {
            FlipperInitializer.interceptor?.let { addInterceptor(it) }
        }
    }

    return okHttpClientBuilder
}

private fun buildOkHttp(
    requestHeaderInterceptor: RequestHeaderInterceptor,
    requestTokenAuthenticator: RequestTokenAuthenticator,
): OkHttpClient {
    val okHttpClientBuilder = basicOkHttpBuilder()
    okHttpClientBuilder.addInterceptor(requestHeaderInterceptor)
    okHttpClientBuilder.authenticator(requestTokenAuthenticator)

    val client = okHttpClientBuilder.build()
    client.dispatcher.maxRequestsPerHost = MAX_REQUESTS_PER_HOST

    return client
}

private fun buildRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}
