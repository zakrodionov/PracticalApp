
02.03.2021 Gradle 6.8.3
Timing:
30m basic setup
30m buildSrc setup

#1
1. Rename settings.gradle to settings.gradle.kts
2. Change text to include(":app")

#2
1. Rename build.gradle to build.gradle.kts
2. Change ext.kotlin_version = "1.4.21" to val kotlinVersion = "1.4.21"
3. Change  classpath "com.android.tools.build:gradle:4.1.2" to  classpath("com.android.tools.build:gradle:4.1.2")
4. Replace clean task to
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

#3
1. Rename app level build.gradle to build.gradle.kts
2. Change plugins to plugins {
                         id("com.android.application")
                         id("kotlin-android")
                     }
3. Change strings by highlighting

#4
1. add to root folder buildSrc
2.
(link https://proandroiddev.com/gradle-groovy-to-kotlin-dsl-in-15-minutes-d3129aff227e)