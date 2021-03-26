Timing:
1h basic setup

В корневой папке создается **common-lib-build.gradle** для переиспользования настройки модулей. 
Используется через :

```
apply from: '../common-lib-build.gradle'
```



Модули **библиотек** и **common-lib-build.gradle** написаны без **.kts** т.к. пока эта возможность затруднена.