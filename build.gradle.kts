plugins {
    id("application")
}

group = "org.mongodb"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mongodb:mongodb-driver-sync:5.+")
    implementation("software.amazon.awssdk:auth:2.+")
    implementation("ch.qos.logback:logback-classic:1.+")
}

application {
    mainClass = "ConnectivityTest"
}

