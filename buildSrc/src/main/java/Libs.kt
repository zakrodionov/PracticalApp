import Versions.adapterDelegatesVersion
import Versions.flipperVersion
import Versions.hyperionVersion
import Versions.koinVersion
import Versions.kotlinVersion
import Versions.lifecycleVersion
import Versions.modoVersion
import Versions.moshiVersion
import Versions.mviOrbitVersion
import Versions.retrofitVersion

object Libs {
    //AndroidX
    const val androidx_app_compat = "androidx.appcompat:appcompat:1.3.0-rc01"
    const val androidx_material = "com.google.android.material:material:1.3.0"
    const val androidx_core = "androidx.core:core-ktx:1.5.0-rc01"
    const val androidx_constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val androidx_fragment = "androidx.fragment:fragment-ktx:1.3.2"

    //Kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3"

    //Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
    const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
    const val moshi_codegen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
    const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"

    //Lifecycle
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"

    // Koin
    const val koin = "io.insert-koin:koin-android:$koinVersion"
    const val koin_test = "io.insert-koin:koin-test:$koinVersion"

    // Orbit Mvi
    const val orbit_mvi = "org.orbit-mvi:orbit-core:$mviOrbitVersion"
    const val orbit_mvi_vm = "org.orbit-mvi:orbit-viewmodel:$mviOrbitVersion"
    const val orbit_mvi_test = "org.orbit-mvi:orbit-test:$mviOrbitVersion"

    // Inspect & Debug tools
    // Flipper
    const val flipper = "com.facebook.flipper:flipper:$flipperVersion"
    const val flipper_network = "com.facebook.flipper:flipper-network-plugin:$flipperVersion"
    const val flipper_no_op = "com.facebook.flipper:flipper-noop:$flipperVersion"
    const val flipper_soloader = "com.facebook.soloader:soloader:0.10.1"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val leak_canary = "com.squareup.leakcanary:leakcanary-android:2.6"

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

    const val viewbinding_property_delegate = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.4.5"

    //AdapterDelegates
    const val adapter_delegates = "com.hannesdorfmann:adapterdelegates4:$adapterDelegatesVersion"
    const val adapter_delegates_dsl =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:$adapterDelegatesVersion"

    const val modo = "com.github.terrakok:modo:$modoVersion"
    const val modo_android = "com.github.terrakok:modo-render-android-fm:$modoVersion"

    const val coil = "io.coil-kt:coil:1.1.1"

    const val state_delegator = "com.redmadrobot:state-delegator:1.7"

    //Test
    const val junit = "junit:junit:4.+"
    const val junit_ext = "androidx.test.ext:junit:1.1.2"
    const val espresso_core = "androidx.test.espresso:espresso-core:3.3.0"
}