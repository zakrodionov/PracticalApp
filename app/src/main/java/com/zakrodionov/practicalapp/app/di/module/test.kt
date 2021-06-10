package com.zakrodionov.practicalapp.app.di.module

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherInterceptorOkHttpClient

// Usage
//@Module
//@InstallIn(ActivityComponent::class)
//object AnalyticsModule {
//
//    @Provides
//    fun provideAnalyticsService(
//        @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
//    ): AnalyticsService {
//        return Retrofit.Builder()
//            .baseUrl("https://example.com")
//            .client(okHttpClient)
//            .build()
//            .create(AnalyticsService::class.java)
//    }
//}
//
//// As a dependency of a constructor-injected class.
//class ExampleServiceImpl @Inject constructor(
//    @AuthInterceptorOkHttpClient private val okHttpClient: OkHttpClient
//) : ...
//
//// At field injection.
//@AndroidEntryPoint
//class ExampleActivity: AppCompatActivity() {
//
//    @AuthInterceptorOkHttpClient
//    @Inject lateinit var okHttpClient: OkHttpClient
//}