plugins {
    id("buildsrc.convention.kotlin-blueprint-lib")
    alias(libs.plugins.kotlinPluginSerialization)
}

val jakartaValidationVersion: String by project

dependencies {
    // Apply the kotlinx bundle of dependencies from the version catalog (`gradle/libs.versions.toml`).
    implementation(libs.bundles.kotlinxEcosystem)

    compileOnly("jakarta.validation:jakarta.validation-api:${jakartaValidationVersion}")

    implementation(project(":blueprint-root"))

    testImplementation(kotlin("test"))
}
