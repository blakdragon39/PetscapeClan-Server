import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"
}

group = "com.blakdragon"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:2.5.6")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:2.5.5")
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.6")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.5.5")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.5.5")
    developmentOnly("org.springframework.boot:spring-boot-devtools:2.5.5")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")

    implementation("com.google.api-client:google-api-client:1.32.2")

    implementation("org.mindrot:jbcrypt:0.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.6")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("com.github.fakemongo:fongo:2.1.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.blakdragon.petscapeclan.PetscapeClanApplicationKt"
    }
}
