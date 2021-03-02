// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
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

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
