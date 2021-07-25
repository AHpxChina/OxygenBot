import org.gradle.kotlin.dsl.accessors.runtime.extensionOf

plugins {
    kotlin("jvm") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    java
}

group = "cn.ahpxchina.oxygen.bot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://csspeechstorage.blob.core.windows.net/maven/")
}

dependencies {
    implementation(kotlin("stdlib"))

    val miraiVersion = "2.6.7"
    implementation("net.mamoe", "mirai-core-api", miraiVersion)
    implementation("net.mamoe", "mirai-core", miraiVersion)

    implementation ("org.jsoup:jsoup:1.14.1")
    implementation ("com.google.code.gson:gson:2.8.7")
    implementation ("org.reflections:reflections:0.9.12")

    implementation ("com.microsoft.cognitiveservices.speech", "client-sdk", "1.17.0", ext = "jar")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>() {
    manifest {
        attributes["Main-Class"] = "LauncherKt"
    }
}