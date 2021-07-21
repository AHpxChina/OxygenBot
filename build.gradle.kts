plugins {
    kotlin("jvm") version "1.5.21"
}

group = "cn.ahpxchina.oxygen.bot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    val miraiVersion = "2.6.7"
    api("net.mamoe", "mirai-core-api", miraiVersion)     // 编译代码使用
    runtimeOnly("net.mamoe", "mirai-core", miraiVersion) // 运行时使用

    implementation ("org.jsoup:jsoup:1.14.1")
    implementation ("com.google.code.gson:gson:2.8.7")
    implementation ("org.reflections:reflections:0.9.12")
}
