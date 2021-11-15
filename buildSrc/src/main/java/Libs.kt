import Versions.accompanistVersion
import Versions.coilVersion
import Versions.composeVersion
import Versions.flipperVersion
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
    const val androidx_app_compat = "androidx.appcompat:appcompat:1.4.0-rc01"
    const val androidx_material = "com.google.android.material:material:1.4.0"
    const val androidx_core = "androidx.core:core-ktx:1.7.0"
    const val androidx_security_crypto = "androidx.security:security-crypto:1.0.0"
    const val androidx_activity_compose = "androidx.activity:activity-compose:1.4.0"
    const val androidx_navigation_compose = "androidx.navigation:navigation-compose:2.4.0-alpha08"

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
    const val koin_compose = "io.insert-koin:koin-androidx-compose:$koinVersion"
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

    // Compose
    const val compose_ui = "androidx.compose.ui:ui:$composeVersion"

    // Tooling support (Previews, etc.)
    const val compose_tooling = "androidx.compose.ui:ui-tooling:$composeVersion"

    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    const val compose_foundation = "androidx.compose.foundation:foundation:$composeVersion"

    // Material Design
    const val compose_material = "androidx.compose.material:material:$composeVersion"

    // Material design icons
    const val compose_material_icons_core = "androidx.compose.material:material-icons-core:$composeVersion"
    const val compose_material_icons_extended = "androidx.compose.material:material-icons-extended:$composeVersion"

    // Coil
    const val coil = "io.coil-kt:coil:$coilVersion"
    const val coil_compose = "io.coil-kt:coil-compose:$coilVersion"

    // Accompanist
    const val accompanist_swipe_refresh = "com.google.accompanist:accompanist-swiperefresh:$accompanistVersion"
    const val accompanist_insets = "com.google.accompanist:accompanist-insets:$accompanistVersion"
    const val accompanist_system_ui_controller = "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion"

    // UI Tests
    const val compose_ui_test = "androidx.compose.ui:ui-test-junit4:$composeVersion"

    const val insetter = "dev.chrisbanes.insetter:insetter:0.6.0"

    //Test
    const val junit_jupiter_api = "org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion"
    const val junit_jupiter_engine = "org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion"
    const val espresso_core = "androidx.test.espresso:espresso-core:3.3.0"
    const val mockk = "io.mockk:mockk:1.12.0"
    const val assertk = "com.willowtreeapps.assertk:assertk-jvm:0.25"
}