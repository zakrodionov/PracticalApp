import Versions.kotlinVersion

object Versions {
    const val kotlinVersion = "1.4.31"
}

object Libs {
    //AndroidX
    const val androidx_app_compat = "androidx.appcompat:appcompat:1.2.0"
    const val androidx_material = "com.google.android.material:material:1.3.0"
    const val androidx_core = "androidx.core:core-ktx:1.3.2"
    const val androidx_constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val androidx_fragment = "androidx.fragment:fragment-ktx:1.3.0"

    //Kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

    //Test
    const val junit = "junit:junit:4.+"
    const val junit_ext = "androidx.test.ext:junit:1.1.2"
    const val espresso_core = "androidx.test.espresso:espresso-core:3.3.0"
}