import AndroidConfig.APK_NAME
import AndroidConfig.APPLICATION_ID
import AndroidConfig.BUILD_TOOLS_VERSION
import AndroidConfig.COMPILE_SDK_VERSION
import AndroidConfig.MIN_SDK_VERSION
import AndroidConfig.TARGET_SDK_VERSION
import AndroidConfig.TEST_INSTRUMENTATION_RUNNER
import AndroidConfig.VERSION_CODE
import AndroidConfig.VERSION_NAME
import Libs.androidx_app_compat
import Libs.androidx_core
import Libs.androidx_material
import Libs.androidx_security_crypto
import Libs.coroutines
import Libs.coroutines_test
import Libs.desugar_jdk
import Libs.espresso_core
import Libs.flipper
import Libs.flipper_network
import Libs.flipper_no_op
import Libs.flipper_soloader
import Libs.junit_jupiter_api
import Libs.junit_jupiter_engine
import Libs.koin
import Libs.koin_compose
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
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("de.mannodermaus.android-junit5")
    id("com.starter.easylauncher").version("4.1.0")
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeVersion
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

    // AndroidX
    implementation(androidx_core)
    implementation(androidx_app_compat)
    implementation(androidx_material)
    implementation(androidx_security_crypto)

    // Koin
    implementation(koin)
    implementation(koin_compose)
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

    coreLibraryDesugaring(desugar_jdk)

    // Compose
    implementation(Libs.compose_ui)
    implementation(Libs.compose_tooling)
    implementation(Libs.compose_foundation)
    implementation(Libs.compose_material)
    implementation(Libs.compose_material_icons_core)
    implementation(Libs.compose_material_icons_extended)
    androidTestImplementation(Libs.compose_ui_test)

    implementation(Libs.androidx_activity_compose)
    implementation(Libs.androidx_navigation_compose)

    implementation(Libs.coil)
    implementation(Libs.coil_compose)

    implementation(Libs.accompanist_swipe_refresh)
    implementation(Libs.accompanist_insets)
    implementation(Libs.accompanist_system_ui_controller)

    val voyager = "1.0.0-beta08" // Todo
    implementation("cafe.adriel.voyager:voyager-navigator:$voyager")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyager")
    implementation("cafe.adriel.voyager:voyager-transitions:$voyager")
    implementation("cafe.adriel.voyager:voyager-androidx:$voyager")
}
