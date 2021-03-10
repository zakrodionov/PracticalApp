import Versions.flipperVersion
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

    // Flipper
    const val flipper = "com.facebook.flipper:flipper:$flipperVersion"
    const val flipper_network = "com.facebook.flipper:flipper-network-plugin:$flipperVersion"
    const val flipper_no_op = "com.facebook.flipper:flipper-noop:$flipperVersion"
    const val flipper_soloader = "com.facebook.soloader:soloader:0.10.1"

    //Test
    const val junit = "junit:junit:4.+"
    const val junit_ext = "androidx.test.ext:junit:1.1.2"
    const val espresso_core = "androidx.test.espresso:espresso-core:3.3.0"
}