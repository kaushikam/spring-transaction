plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.jpa") version "1.3.72"
    kotlin("plugin.allopen") version "1.3.72"
    kotlin("plugin.noarg") version "1.3.72"
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

group = "com.kaushikam"
version = "0.0.1.SNAPSHOT"

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Table")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Table")
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.vladmihalcea:hibernate-types-52:2.9.13")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("ch.qos.logback:logback-classic")
    implementation("org.codehaus.groovy:groovy:3.0.5")
    implementation("io.github.microutils:kotlin-logging:1.8.3")

    runtimeOnly("com.h2database:h2:1.4.200")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "mockito-core")
    }
    testImplementation(kotlin("test"))
    testImplementation("com.ninja-squad:springmockk:2.0.2")
    testImplementation("org.dbunit:dbunit:2.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}