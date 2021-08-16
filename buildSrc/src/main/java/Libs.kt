import Versions.adapterDelegatesVersion
import Versions.flipperVersion
import Versions.glideVersion
import Versions.hyperionVersion
import Versions.junitJupiterVersion
import Versions.koinVersion
import Versions.kotlinCoroutinesVersion
import Versions.kotlinVersion
import Versions.lifecycleVersion
import Versions.moshiVersion
import Versions.retrofitVersion

object Libs {
    //AndroidX
    const val androidx_app_compat = "androidx.appcompat:appcompat:1.3.1"
    const val androidx_material = "com.google.android.material:material:1.4.0"
    const val androidx_core = "androidx.core:core-ktx:1.6.0"
    const val androidx_constraint_layout = "androidx.constraintlayout:constraintlayout:2.1.0"
    const val androidx_fragment = "androidx.fragment:fragment-ktx:1.3.6"
    const val androidx_swipe_refresh_layout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    const val androidx_security_crypto = "androidx.security:security-crypto:1.0.0"

    //Kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinCoroutinesVersion"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutinesVersion"

    //Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
    const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
    const val moshi_codegen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
    const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"

    //Lifecycle
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"

    // Koin
    const val koin = "io.insert-koin:koin-android:$koinVersion"
    const val koin_test = "io.insert-koin:koin-test:$koinVersion"

    // Inspect & Debug tools
    // Flipper
    const val flipper = "com.facebook.flipper:flipper:$flipperVersion"
    const val flipper_network = "com.facebook.flipper:flipper-network-plugin:$flipperVersion"
    const val flipper_no_op = "com.facebook.flipper:flipper-noop:$flipperVersion"
    const val flipper_soloader = "com.facebook.soloader:soloader:0.10.1"

    const val timber = "com.jakewharton.timber:timber:5.0.1"

    const val leak_canary = "com.squareup.leakcanary:leakcanary-android:2.7"

    // Hyperion
    const val hyperion_core = "com.willowtreeapps.hyperion:hyperion-core:$hyperionVersion"
    const val hyperion_attr = "com.willowtreeapps.hyperion:hyperion-attr:$hyperionVersion"
    const val hyperion_build_config = "com.willowtreeapps.hyperion:hyperion-build-config:$hyperionVersion"
    const val hyperion_crash = "com.willowtreeapps.hyperion:hyperion-crash:$hyperionVersion"
    const val hyperion_disk = "com.willowtreeapps.hyperion:hyperion-disk:$hyperionVersion"
    const val hyperion_geiger_counter = "com.willowtreeapps.hyperion:hyperion-geiger-counter:$hyperionVersion"
    const val hyperion_measurement = "com.willowtreeapps.hyperion:hyperion-measurement:$hyperionVersion"
    const val hyperion_phoenix = "com.willowtreeapps.hyperion:hyperion-phoenix:$hyperionVersion"
    const val hyperion_shared_preferences = "com.willowtreeapps.hyperion:hyperion-shared-preferences:$hyperionVersion"
    const val hyperion_timber = "com.willowtreeapps.hyperion:hyperion-timber:$hyperionVersion"

    const val okhttp = "com.squareup.okhttp3:okhttp:4.9.0"

    const val desugar_jdk = "com.android.tools:desugar_jdk_libs:1.1.5"

    const val viewbinding_property_delegate = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.4.7"

    //AdapterDelegates
    const val adapter_delegates = "com.hannesdorfmann:adapterdelegates4:$adapterDelegatesVersion"
    const val adapter_delegates_dsl =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:$adapterDelegatesVersion"

    const val cicerone = "com.github.terrakok:cicerone:7.1"

    const val decoro = "ru.tinkoff.decoro:decoro:1.5.0"

    const val coil = "io.coil-kt:coil:1.1.1"

    //Glide
    const val glide = "com.github.bumptech.glide:glide:$glideVersion"
    const val glide_compiler = "com.github.bumptech.glide:compiler:$glideVersion"

    const val insetter = "dev.chrisbanes.insetter:insetter:0.6.0"

    //Test
    const val junit_jupiter_api = "org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion"
    const val junit_jupiter_engine = "org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion"
    const val espresso_core = "androidx.test.espresso:espresso-core:3.3.0"
    const val mockk = "io.mockk:mockk:1.11.0"
}