plugins {
    id("buildsrc.convention.kotlin-jvm")
    application
}

val smallryeConfigVersion: String by project
val quarkusVersion: String by project

dependencies {
    implementation(project(":blueprint"))
    implementation("io.smallrye.config:smallrye-config:${smallryeConfigVersion}")
    implementation("io.quarkus:quarkus-config-yaml:${quarkusVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.22.0")
}

application {
    mainClass = "it.lysz210.akasha.capacnan.scriptbuilder.MainKt"
}