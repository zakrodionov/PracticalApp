import Versions.flipperVersion
import Versions.hyperionVersion
import Versions.kotlinVersion

object Libs {
    //AndroidX
    const val androidx_app_compat = "androidx.appcompat:appcompat:1.2.0"
    const val androidx_material = "com.google.android.material:material:1.3.0"
    const val androidx_core = "androidx.core:core-ktx:1.3.2"
    const val androidx_constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val androidx_fragment = "androidx.fragment:fragment-ktx:1.3.0"

    //Kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

    // Inspect & Debug tools
    // Flipper
    const val flipper = "com.facebook.flipper:flipper:$flipperVersion"
    const val flipper_network = "com.facebook.flipper:flipper-network-plugin:$flipperVersion"
    const val flipper_no_op = "com.facebook.flipper:flipper-noop:$flipperVersion"
    const val flipper_soloader = "com.facebook.soloader:soloader:0.10.1"

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

    //Test
    const val junit = "junit:junit:4.+"
    const val junit_ext = "androidx.test.ext:junit:1.1.2"
    const val espresso_core = "androidx.test.espresso:espresso-core:3.3.0"
}