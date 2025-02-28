plugins {
    id("application")
}

group = "org.mongodb"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mongodb:mongodb-driver-sync:5.2.1")
    implementation("software.amazon.awssdk:auth:2.30.30")
    implementation("ch.qos.logback:logback-classic:1.3.14")
}

application {
    mainClass = "ConnectivityTest"
}

