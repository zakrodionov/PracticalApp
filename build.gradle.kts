// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(Plugins.android_gradle_plugin)
        classpath(Plugins.kotlin_gradle_plugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

plugins {
    id(Plugins.detekt_plugin).version(Versions.detektVersion)
    id(Plugins.ktlint_plugin).version(Versions.ktlintVersion)
    id(Plugins.libs_versions_plugin).version(Versions.libsVersionsPluginVersion)
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false
    config =
        files("$projectDir/config/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
    baseline = file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt

    reports {
        html.enabled = true
        txt.enabled = true
    }
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    rejectVersionIf {
        isNonStable(candidate.version)
    }

    checkForGradleUpdate = true
    outputFormatter = "html"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}

fun isNonStable(version: String): Boolean {
    val rejectedTag = listOf("alpha", "beta", /*"rc"*/ "cr", "m", "preview")
    val rejected = rejectedTag.any { qualifier ->
        version.matches(Regex("(?i).*[.-]$qualifier[.\\d-+]*"))
    }
    return rejected
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
