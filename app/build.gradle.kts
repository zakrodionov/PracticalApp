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
import Libs.androidx_constraintlayout
import Libs.androidx_core
import Libs.androidx_fragment
import Libs.androidx_material
import Libs.espresso_core
import Libs.flipper
import Libs.flipper_network
import Libs.flipper_no_op
import Libs.flipper_soloader
import Libs.junit
import Libs.junit_ext
import Libs.kotlin_stdlib
import Libs.leak_canary
import Libs.timber

plugins {
    id("com.android.application")
    id("kotlin-android")
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

    buildTypes {
        getByName("release") {
            versionNameSuffix = "release"
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            versionNameSuffix = "debug"
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(kotlin_stdlib)

    testImplementation(junit)
    androidTestImplementation(junit_ext)
    androidTestImplementation(espresso_core)

    // AndroidX
    implementation(androidx_core)
    implementation(androidx_app_compat)
    implementation(androidx_material)
    implementation(androidx_constraintlayout)
    implementation(androidx_fragment)

    // Flipper
    debugImplementation(flipper)
    debugImplementation(flipper_network)
    debugImplementation(flipper_soloader)
    releaseImplementation(flipper_no_op)

    implementation(timber)

    debugImplementation(leak_canary)
}
