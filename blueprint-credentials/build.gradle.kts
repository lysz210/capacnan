plugins {
    id("buildsrc.convention.kotlin-blueprint-lib")
    alias(libs.plugins.kotlinPluginSerialization)
}

dependencies {
    // Apply the kotlinx bundle of dependencies from the version catalog (`gradle/libs.versions.toml`).
    implementation(libs.bundles.kotlinxEcosystem)

    testImplementation(kotlin("test"))
}
