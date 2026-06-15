plugins {
    id("buildsrc.convention.kotlin-jvm")
    kotlin("plugin.allopen") version "2.3.21"
    id("io.quarkus")
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

val jetstreamVersion: String by project

data class BlueprintModule(
    val gradlePath: String,
    val configFile: String
)

val blueprintModules = listOf(
    BlueprintModule(":blueprint-root", "blueprint-root.yaml"),
    BlueprintModule(":blueprint-geo", "blueprint-geo.yaml"),
    BlueprintModule(":blueprint-credentials", "blueprint-credentials.yaml")
)

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))

    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-vertx-http")

    implementation("io.nats:jnats:2.25.3")
    blueprintModules.forEach { blueprint ->
        implementation(project(blueprint.gradlePath))
    }

    testImplementation("io.quarkus:quarkus-junit")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("org.testcontainers:testcontainers:1.19.7")
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25
        javaParameters = true
    }
}

tasks.processResources {
    blueprintModules.forEach { blueprint ->
        mustRunAfter(project(blueprint.gradlePath).tasks.processResources)
        from(project(blueprint.gradlePath).file("src/main/resources/${blueprint.configFile}")) {
            into("META-INF/resources")
        }
    }
}

quarkus {

}