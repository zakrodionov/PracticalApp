# PracticalApp - Compose [WIP] [![Build](https://github.com/zakrodionov/PracticalApp/actions/workflows/Build.yml/badge.svg)](https://github.com/zakrodionov/PracticalApp/actions/workflows/Build.yml)

PracticalApp is an application architecture example for commercial/outsourced development. Main goals: efficiency, ease of maintaining the code, the use of tools that do not require long study (Android Architecture Components, Koin, Coroutines). The principles of Clean Architecture are followed, but without strict fanaticism, not at the expense of performance (without abstractions for the sake of abstractions).

1. Interfaces must be written on the repository
2. The interactor is used only if you need common logic on several screens, and not just duplicate the repository call
3. If the model used in the UI layer, for example, in the Rv-adapter, is the same as in Domain / Data, then it can be marked with annotations from Moshi / Gson / Room in the same place so as not to duplicate it
and do not create an extra mapper.

For convenience, the `:common` module has been added, which contains utilities, extension functions and custom views / classes used in
most projects and the `:coreui` module which contains themes, styles, colors for the project. If 1-3 people are working on the project, feature modules can be left out and written as usual, in the main module.

# Code debugging/development
For the convenience of debugging code during development, several libraries are used: **Flipper (replacing Stetho), Hyperion, Timber, LeakCanary**.
Flipper, Hyperion allow you to intercept network calls, view the contents of SharedPrefernces, DataBases, have built-in layout inspectors, color pickers and other tools for developers.

# Compliance with code-style
**Detekt, Ktlint** libraries are used to maintain a uniform code style. Before pushing, you should check the new code for compliance with the rules using the `gradlew ktFormat && gradlew detekt` command, or configure
task in Android Studio. CI is also configured with Detekt, Ktlint to check the project with Push, Pull Request.

# Code cleanliness
Android Studio has a pretty good built-in linter, launched using the `gradlew lint` command. Allows you to identify Deprecated APIs, unused resources, security, performance, usability, accessibility issues, etc....


## Stack
• Android Jetpack + Compose

• Kotlin

• MV/I/VM 

• Retrofit, Coroutines, Moshi, OkHttp

• Android ViewModel

• Voyager

• Koin

• Coil

• Desugar Jdk (Java 8 time)

• Flipper, Hyperion, Timber, LeakCanary

• Github Actions

• Gradle DSL

• Gradle Modules

What the sample looks like:
   
<img src="https://user-images.githubusercontent.com/27068529/119772938-9c4d9980-bec8-11eb-9512-319c4f427e4b.jpg" alt="Пример, как выглядит сэмпл" width="30%">

