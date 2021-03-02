
02.03.2021 Gradle 6.8.3    
Timing:    
30m basic setup    
60m buildSrc setup   

## Basic Setup
### 1
1. Rename **settings.gradle** to **settings.gradle.kts**
2. Change text to `include(":app")`

### 2
1. Rename **build.gradle** to **build.gradle.kts**
2. Change `ext.kotlin_version = "1.4.21"` to `val kotlinVersion = "1.4.21"`
3. Change `classpath "com.android.tools.build:gradle:4.1.2"` to  `classpath("com.android.tools.build:gradle:4.1.2")`
4. Replace clean task to
```kotlin
tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
```

### 3
1. Rename app level **build.gradle** to **build.gradle.kts**
2. Change plugins to
```kotlin
plugins {
    id("com.android.application")
    id("kotlin-android")
}
```
3. Change strings by highlighting

## BuildSrc Setup
1. add to root folder **buildSrc**
2. add file **build.gradle.kts**

```kotlin
plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}
```

3. Add folder **src/main/java**
4. Add dependencies
5. Replace dependencies to use BuildSrc
6. *Note. Use in project  `classpath(Plugins.android_gradle_plugin)` without imports because of a bug.

[Link](https://proandroiddev.com/gradle-groovy-to-kotlin-dsl-in-15-minutes-d3129aff227e "Link")
