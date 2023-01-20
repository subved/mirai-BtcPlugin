import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.5.30"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("com.github.johnrengelman.shadow") version "1.2.3"
    id("net.mamoe.mirai-console") version "2.9.1"
}

group = "com.subved"
version = "1.2-SNAPSHOT"

repositories {

    maven("https://maven.aliyun.com/repository/public")
    maven { url =uri("https://maven.aliyun.com/nexus/content/repositories/jcenter")}
    maven {url = uri("https://repo.maven.apache.org/maven2/net/mamoe/")}
    maven("https://repo.maven.apache.org/maven2/net/mamoe/" )
    mavenLocal()
    mavenCentral()
    jcenter()

}

dependencies{
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.jar{
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}