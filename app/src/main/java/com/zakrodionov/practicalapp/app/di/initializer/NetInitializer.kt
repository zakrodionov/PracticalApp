package com.zakrodionov.practicalapp.app.di.initializer

import com.squareup.moshi.Moshi
import com.zakrodionov.common.extensions.isDebug
import com.zakrodionov.common.utils.net.NetworkHandler
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.FlipperInitializer
import com.zakrodionov.practicalapp.data.remote.ApiPost
import com.zakrodionov.practicalapp.data.remote.interceptor.MainHeaderInterceptor
import com.zakrodionov.practicalapp.data.remote.interceptor.MainTokenAuthenticator
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
    private const val NAME_MAIN_OK_HTTP = "mainOkHttp"
    private const val NAME_MAIN_RETROFIT = "mainRetrofit"

    override fun initialize(appModule: Module) {
        appModule.apply {
            single { buildMoshi() }

            single { NetworkHandler(get()) }

            single { MainHeaderInterceptor(get()) }
            single { MainTokenAuthenticator() }

            single(named(NAME_MAIN_OK_HTTP)) { buildMainOkHttp(get(), get()) }
            single(named(NAME_MAIN_RETROFIT)) { buildMainRetrofit(get()) }

            single { buildApiPost(get(named(NAME_MAIN_RETROFIT))) }
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

    // start build APIs
    private fun buildApiPost(retrofit: Retrofit): ApiPost {
        return retrofit.create(ApiPost::class.java)
    }
}
