# PracticalApp  [![Build](https://github.com/zakrodionov/PracticalApp/actions/workflows/Build.yml/badge.svg)](https://github.com/zakrodionov/PracticalApp/actions/workflows/Build.yml)

PracticalApp - пример архитектуры приложения для коммерческой/аутсорс разработки. Основные цели: простота ведения и поддержания кода, использования инструментов, которые не требуют
длительного изучения (Android Jetpack, Koin, Mvi-Orbit, Coroutines). Принципы Clean Architecture придерживаются, но не строго и без фанатизма, не в ущерб производительности (без абстракций ради абстракций).

1. На репозитории интерфейсы пишутся обязатльно
2. Интерактор используется только в случае если нужна общая логика на нескольких, а не просто продублировать вызов репозитория
3. Если модель используемая в UI слое, например в Rv-адаптере, такая же что и в Domain/Data, то ее можно помечать аннотациями от Moshi/Gson/Room там же, дабы не дублировать ее
и не создавать лишний маппер.

Для удобства использвания, и не захломления основного модуля, выделен модуль `:common`, который содержит утилиты, функции расширения и кастомные вью/классы, используемые в
большинства проектов. Если над проектом работает 1-3 человека, feature модули можно не выделять, а писать их как обычно, в главном модуле.

# Отладка кода/разработка
Для удобства отладки кода во время разработки используется несколько библиотек: **Flipper(замена Stetho), Hyperion, Timber, LeakCanary**.
Flipper, Hyperion позволяют перехватить вызовы сети, просматривать содержимое SharedPrefernces, DataBases, имеют встроенные layout-инспекторы, color-пикеры и другие инструменты для разработчиков.

# Соблюдение code-style
Для соблюдения единого стиля кода используется библиотеки **Detekt, Ktlint**. Перед пушем следует проверять новый код на соблюдение правил с помощью команды `gradlew ktFormat && gradlew detekt`, либо настроить
таску в Android Studio. CI так же настроен с Detekt, Ktlint на проверку проекта при Push, Pull Request.

## Stack
• Kotlin

• Github Actions

• Gradle DSL

• Gradle Modules

• Android Jetpack

• MV/I/VM 

• Modo

• Retrofit, Coroutines, Moshi, OkHttp

• Android ViewModel, Mvi-Orbit

• Koin

• Room

• Adapter Delegates 4

• Coil

• ViewBinding

• Desugar Jdk (Java 8 time)

• Flipper, Hyperion, Timber, LeakCanary

• State Delegator

