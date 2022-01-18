import AndroidConfig.APK_NAME
import AndroidConfig.APPLICATION_ID
import AndroidConfig.BUILD_TOOLS_VERSION
import AndroidConfig.COMPILE_SDK_VERSION
import AndroidConfig.MIN_SDK_VERSION
import AndroidConfig.TARGET_SDK_VERSION
import AndroidConfig.TEST_INSTRUMENTATION_RUNNER
import AndroidConfig.VERSION_CODE
import AndroidConfig.VERSION_NAME
import Libs.adapter_delegates
import Libs.adapter_delegates_dsl
import Libs.androidx_app_compat
import Libs.androidx_constraint_layout
import Libs.androidx_core
import Libs.androidx_fragment
import Libs.androidx_material
import Libs.androidx_security_crypto
import Libs.androidx_swipe_refresh_layout
import Libs.assertk
import Libs.cicerone
import Libs.coroutines
import Libs.coroutines_test
import Libs.desugar_jdk
import Libs.espresso_core
import Libs.flipper
import Libs.flipper_network
import Libs.flipper_no_op
import Libs.flipper_soloader
import Libs.hyperion_attr
import Libs.hyperion_build_config
import Libs.hyperion_core
import Libs.hyperion_disk
import Libs.hyperion_geiger_counter
import Libs.hyperion_measurement
import Libs.hyperion_phoenix
import Libs.hyperion_shared_preferences
import Libs.hyperion_timber
import Libs.insetter
import Libs.junit_jupiter_api
import Libs.junit_jupiter_engine
import Libs.koin
import Libs.koin_test
import Libs.kotlin_stdlib
import Libs.leak_canary
import Libs.lifecycle_extensions
import Libs.lifecycle_runtime_ktx
import Libs.lifecycle_viewmodel
import Libs.mockk
import Libs.moshi
import Libs.moshi_codegen
import Libs.okhttp
import Libs.okhttp_logging_interceptor
import Libs.retrofit
import Libs.retrofit_moshi
import Libs.timber
import Libs.viewbinding_property_delegate
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("de.mannodermaus.android-junit5")
    id("com.starter.easylauncher").version("5.0.0")
    id(Plugins.detekt_plugin)
    id(Plugins.ktlint_plugin)
}

android {
    compileSdkVersion(COMPILE_SDK_VERSION)
    buildToolsVersion(BUILD_TOOLS_VERSION)

    defaultConfig {
        applicationId = APPLICATION_ID
        minSdkVersion(MIN_SDK_VERSION)
        targetSdkVersion(TARGET_SDK_VERSION)
        versionCode = VERSION_CODE
        versionName = VERSION_NAME

        setProperty("archivesBaseName", APK_NAME)

        testInstrumentationRunner = TEST_INSTRUMENTATION_RUNNER
    }

    signingConfigs {
        create("release") {
            val keystorePropertiesFile = rootProject.file("keystore.properties")
            if (keystorePropertiesFile.exists()) {
                val keystoreProperties = Properties().apply { load(FileInputStream(keystorePropertiesFile)) }

                with(keystoreProperties) {
                    storeFile = rootProject.file(this["RELEASE_STORE_FILE"] as String)
                    storePassword = this["RELEASE_STORE_PASSWORD"] as String
                    keyAlias = this["RELEASE_KEY_ALIAS"] as String
                    keyPassword = this["RELEASE_KEY_PASSWORD"] as String
                }
            }

            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        getByName("release") {
            versionNameSuffix = "-release"
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs["release"]

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            buildConfigField("String", "API_URL", AndroidConfig.API_DEBUG_URL)
        }

        getByName("debug") {
            versionNameSuffix = "-debug"

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            buildConfigField("String", "API_URL", AndroidConfig.API_DEBUG_URL)
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":coreui"))

    implementation(kotlin_stdlib)
    implementation(coroutines)

    // Test
    androidTestImplementation(espresso_core)
    testImplementation(junit_jupiter_api)
    testImplementation(junit_jupiter_engine)
    testImplementation(mockk)
    testImplementation(coroutines_test)
    testImplementation(assertk)

    // AndroidX
    implementation(androidx_core)
    implementation(androidx_app_compat)
    implementation(androidx_material)
    implementation(androidx_constraint_layout)
    implementation(androidx_fragment)
    implementation(androidx_swipe_refresh_layout)
    implementation(androidx_security_crypto)

    // Koin
    implementation(koin)
    testImplementation(koin_test)

    // Networking
    implementation(retrofit)
    implementation(retrofit_moshi)
    implementation(moshi)
    kapt(moshi_codegen)
    implementation(okhttp)
    implementation(okhttp_logging_interceptor)

    // Lifecycle
    implementation(lifecycle_extensions)
    implementation(lifecycle_viewmodel)
    implementation(lifecycle_runtime_ktx)

    // region Debug Tools
    // Flipper
    debugImplementation(flipper)
    debugImplementation(flipper_network)
    debugImplementation(flipper_soloader)
    releaseImplementation(flipper_no_op)

    implementation(timber)

    debugImplementation(leak_canary)

    // Hyperion
    debugImplementation(hyperion_core)
    debugImplementation(hyperion_attr)
    debugImplementation(hyperion_build_config)
    debugImplementation(hyperion_disk)
    debugImplementation(hyperion_geiger_counter)
    debugImplementation(hyperion_measurement)
    debugImplementation(hyperion_phoenix)
    debugImplementation(hyperion_shared_preferences)
    debugImplementation(hyperion_timber)
    // endregion

    coreLibraryDesugaring(desugar_jdk)

    implementation(viewbinding_property_delegate)

    // AdapterDelegates
    implementation(adapter_delegates)
    implementation(adapter_delegates_dsl)

    // Cicerone
    implementation(cicerone)

    implementation(insetter)
}
