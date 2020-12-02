# apibase
## java version
#### ZGC 와 records, text block, seal class 와 같은 최신 문법을 사용하기 위해, amazon 의 java 15 버전을 사용합니다. "15.0.0-amzn"
## java 15 설치방법
### 다운로드
```
$ curl -s "https://get.sdkman.io" | bash
```
### sdk man 실행
```
$ source "$HOME/.sdkman/bin/sdkman-init.sh"
```
### annotation process 를 지원하는 java 15 amazon 버전으로 인스톨
```
$ sdk i java 15.0.0-amzn
```
### 지금 디렉토리에서 java 15 버전으로 변경
```
$ sdk u java 15.0.0-amzn
```
### 어플리케이션 빌드하는 방법
```
./gradlew clean build
```
### 어플리케이션 실행하는 방법
```
java -jar -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -Xms512m -Xmx1g --enable-preview -Dspring.profiles.active=local ./api/build/libs/api-0.0.1-SNAPSHOT.jar
```