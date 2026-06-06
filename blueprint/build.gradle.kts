plugins {
    `java-library`
    id("buildsrc.convention.kotlin-jvm")
    alias(libs.plugins.kotlinPluginSerialization)
}

val smallryeConfigVersion: String by project
val quarkusConfigYamlVersion: String by project

dependencies {
    // Apply the kotlinx bundle of dependencies from the version catalog (`gradle/libs.versions.toml`).
    implementation(libs.bundles.kotlinxEcosystem)
    implementation("io.smallrye.config:smallrye-config:${smallryeConfigVersion}")
    testImplementation("io.quarkus:quarkus-config-yaml:${quarkusConfigYamlVersion}")
    testImplementation(kotlin("test"))
}
