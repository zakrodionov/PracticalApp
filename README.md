# PracticalApp  [![Build](https://github.com/zakrodionov/PracticalApp/actions/workflows/Build.yml/badge.svg)](https://github.com/zakrodionov/PracticalApp/actions/workflows/Build.yml)

### [Compose version (Voyager Navigation) [Work in Progress]](https://github.com/zakrodionov/PracticalApp/tree/feature/compose_voyager_navigation)
###### [Compose version (Google Navigation) [Work in Progress]](https://github.com/zakrodionov/PracticalApp/tree/feature/compose)

PracticalApp - пример архитектуры приложения для коммерческой/аутсорс разработки. Основные цели: эффективность, простота ведения и поддержания кода, использования инструментов, которые не требуют
длительного изучения (Android Architecture Components, Koin, Coroutines). Принципы Clean Architecture придерживаются, но без строгого фанатизма, не в ущерб производительности (без абстракций ради абстракций).

1. На репозитории интерфейсы пишутся обязатльно
2. Интерактор используется только в случае если нужна общая логика на нескольких экранах, а не просто продублировать вызов репозитория
3. Если модель используемая в UI слое, например в Rv-адаптере, такая же что и в Domain/Data, то ее можно помечать аннотациями от Moshi/Gson/Room там же, дабы не дублировать ее
и не создавать лишний маппер.

Для удобства выделен модуль `:common`, который содержит утилиты, функции расширения и кастомные вью/классы, используемые в
большинства проектов и модуль `:coreui` который содержит темы, стили, цвета для проекта. Если над проектом работает 1-3 человека, feature модули можно не выделять, а писать их как обычно, в главном модуле.

# Отладка кода/разработка
Для удобства отладки кода во время разработки используется несколько библиотек: **Flipper(замена Stetho), Hyperion, Timber, LeakCanary**.
Flipper, Hyperion позволяют перехватить вызовы сети, просматривать содержимое SharedPrefernces, DataBases, имеют встроенные layout-инспекторы, color-пикеры и другие инструменты для разработчиков.

# Соблюдение code-style
Для соблюдения единого стиля кода используется библиотеки **Detekt, Ktlint**. Перед пушем следует проверять новый код на соблюдение правил с помощью команды `./gradlew ktFormat && ./gradlew detekt`, либо настроить
таску в Android Studio. CI так же настроен с Detekt, Ktlint на проверку проекта при Push, Pull Request.

# Чистота кода
Android Studio имеет достаточно неплохой встроенный линтер, запускается с помощью команды `./gradlew lint`. Позволяет выявлять Deprecated API, неиспользуемые ресурсы, проблемы безопасности, производительности, юзабилити, доступности и т.д.

## Stack
• Android Jetpack

• Kotlin

• MV/I/VM 

• Cicerone/Modo

• Retrofit, Coroutines, Moshi, OkHttp

• Android ViewModel

• Koin

• Room

• Adapter Delegates 4

• Glide

• ViewBinding

• Desugar Jdk (Java 8 time)

• Flipper, Hyperion, Timber, LeakCanary

• Github Actions

• Gradle DSL

• Gradle Modules

 Как выглядит сэмпл:  
   
<img src="https://user-images.githubusercontent.com/27068529/136216529-48764e6f-9146-48cf-b99a-d61a6acc6cf7.jpg" alt="Пример, как выглядит сэмпл" width="30%">
