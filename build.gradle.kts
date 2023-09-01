plugins {
    java
}

tasks.withType(Wrapper::class) {
    gradleVersion = "7.5.1"
}

group = "io.qameta.allure.examples"
version = 1.3

val allureVersion = "2.24.0"
val cucumberVersion = "7.13.0"
val aspectJVersion = "1.9.20"

tasks.withType(JavaCompile::class) {
    sourceCompatibility = "${JavaVersion.VERSION_1_8}"
    targetCompatibility = "${JavaVersion.VERSION_1_8}"
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

tasks.test {
    ignoreFailures = true
    useJUnitPlatform()
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
}

dependencies {
    agent("org.aspectj:aspectjweaver:${aspectJVersion}")

    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("io.cucumber:cucumber-java")

    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-cucumber7-jvm")
    testImplementation("io.qameta.allure:allure-junit-platform")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("org.junit.jupiter:junit-jupiter-api")

    testImplementation("org.slf4j:slf4j-simple:1.7.30")
}

repositories {
    mavenCentral()
}
