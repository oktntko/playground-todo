# SpringBoot を試そう

データベースを使えば、ウェブシステム化できたといえるのでしょうか？

できていません。

- ウェブシステム化すると、同時に多数の人がデータを操作する
- ファイルでは同時に多数の人がデータを操作することができない
- 同時に多数の人がデータを操作することができるのはデータベース

だからファイルからデータベースを使うようにしました。

ウェブシステムとは何でしょうか？
どうすればウェブシステム化できたといえるでしょうか？

ウェブシステムは、インターネットを介して利用できるシステムのことです。

インターネットを介してシステムを利用できるようにするにはどうすればいいでしょうか？

簡単に言うと、インターネットを介して通信を行う際のルールに従う必要があります。
そのルールかつ手段が、`HTTP`です。ウェブページのURLに必ずついていますね。
`HTTP`のルールに従って`HTTP`を使って通信することで、インターネットを介してシステムを利用できるようになります。

つまり、どうすればいいか？

現状、

コンソールに入力してコンソールに出力する

という状態です。そこから、

HTTPに入力してHTTPに出力する

状態にします。これでウェブシステム化ができます。

**HTTPに入力**を**リクエスト**、**HTTPに出力**を**レスポンス**といいます。

## SpringBootを使う

```kotlin title="build.gradle.kts" hl_lines="4-6 25-29 37-40"
plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    java
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "playground.todo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.fusesource.jansi/jansi
    implementation("org.fusesource.jansi:jansi:2.4.0")
    // https://mvnrepository.com/artifact/de.codeshelf.consoleui/consoleui
    implementation("de.codeshelf.consoleui:consoleui:0.0.13")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.6.0")
    // https://mvnrepository.com/artifact/com.konghq/unirest-java
    implementation("com.konghq:unirest-java:3.14.2")

    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

application {
    // Define the main class for the application.
    mainClass.set("playground.todo.FirstApp")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

```
