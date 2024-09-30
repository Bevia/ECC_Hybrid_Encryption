plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.bouncycastle:bcprov-jdk15to18:1.70")  // BouncyCastle for cryptography
    implementation("org.bouncycastle:bcpkix-jdk15to18:1.70") // BouncyCastle for ECC and ECIES
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}