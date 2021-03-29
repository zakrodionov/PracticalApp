
Timing:
1h basic setup

1h build setup

1h sign build setup

Its timings for github. For gitlab +2h

Для настройки **Github Actions** достаточно использовать приведенные примеры, для других vc настраивается похожим образом.

Так же необходимо реализовать подпись .apk и .aab локальным и удаленным способом через CI/CD. 

Создаем файлы  **practicalapp.jks** c помощью Android Studio и вручную **keystore.properties** и записываем туда информацию для подписи апк.

```
# Put in root folder

RELEASE_STORE_FILE=practicalapp.jks
RELEASE_KEY_ALIAS=practicalapp
RELEASE_STORE_PASSWORD=practicalapp
RELEASE_KEY_PASSWORD=practicalapp
```



Т.к. мы не можем хранить файл .jks  и keystore.properties в репозитории и Secrets сервисах, переводим их в base64, например с помощью программы GnuPG ([Simple installer for the current *GnuPG*](https://gnupg.org/download/index.html "Simple installer for the current *GnuPG*")).  Исходники **practicalapp.jks** и **keystore.properties** бэкапим в Google Files или другом удобном нам месте.

Конвертим файлы в Base64 и сохраняем в Secrets эти файлы в виде строки и ключ для расшифровки там же.

```shell
gpg -c --armor practicalapp.jks
gpg -c --armor keystore.properties
```



Пример распаковки файла в Github Actions обратно из .asc. Эти команды извлекут файлы из Base64 и положат их в корневую папку проекта.

```yaml
- name: Prepare Secret Files
        run: |
              echo "${{ secrets.KEYSTORE }}" > practicalapp.jks.asc
              gpg -d --passphrase "${{ secrets.KEYSTORE_PASSPHRASE }}" --batch practicalapp.jks.asc > practicalapp.jks
              echo "${{ secrets.KEYSTORE_PROPERTIES }}" > keystore.properties.asc
              gpg -d --passphrase "${{ secrets.KEYSTORE_PASSPHRASE }}" --batch keystore.properties.asc > keystore.properties
```



Для локальной подписи .apk достаточно в корневую папку добавить practicalapp.jks и keystore.properties, и запустить подпись.

Для использования cache нужно добавить скрипт **checksum.sh** и сдeлать его исполняемым `git update-index --chmod=+x checksum.sh`

Стригеррить CI можно командой `git commit --allow-empty -m "Trigger Build"`

На всякий случай **надо добавить эти файлы в .gitignore**, чтобы случайно не запушить в git.

```
/keystore.properties
/practicalapp.jks
```



Возможно нам понадобиться что-то добавить в environments, это можно сделать по примеру:

```
env:
  RELEASE_STORE_FILE: ${{ secrets.RELEASE_STORE_FILE }}
  RELEASE_STORE_PASSWORD: ${{ secrets.RELEASE_STORE_PASSWORD }}
  RELEASE_KEY_ALIAS: ${{ secrets.RELEASE_KEY_ALIAS }}
  RELEASE_KEY_PASSWORD: ${{ secrets.RELEASE_KEY_PASSWORD }}
```



Для тестирования CI можно использовать пустые коммиты:

```
git commit --allow-empty -m "Empty commit"
git push
```

