val kotlin_version = "2.0.20"
val logback_version = "1.5.8"
val ktor_version = "2.3.12"
val kotlinx_serialization_version = "1.7.2"

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.20"
    id("io.ktor.plugin") version "2.3.12"
    kotlin("plugin.serialization") version "2.0.20"
}

group = "ru.mymess"
version = "0.0.1"

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

application {
    mainClass.set("ru.mymess.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cio:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("io.ktor:ktor-server-netty:$ktor_version")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // Database
    implementation("org.xerial:sqlite-jdbc:3.41.2.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.7.3")
    implementation(kotlin("stdlib-jdk8"))

    // WebSocket
    implementation("io.ktor:ktor-server-websockets:$ktor_version")
}

kotlin {
    jvmToolchain(19)
}
