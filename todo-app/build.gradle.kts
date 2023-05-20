plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
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
}

application {
    // Define the main class for the application.
    mainClass.set("playground.todo.FirstApp")
}
