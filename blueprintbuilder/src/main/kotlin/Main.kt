package it.lysz210.akasha.capacnan.scriptbuilder

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.smallrye.config.SmallRyeConfigBuilder
import io.smallrye.config.source.yaml.YamlConfigSource
import it.lysz210.akasha.capacnan.blueprint.CapacnanBlueprint
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val blueprintType = CapacnanBlueprint::class.java

    val url = blueprintType.classLoader.getResource("blueprint.yaml")
        ?: throw IllegalArgumentException("blueprint.yaml not found!")
    val yaml = YamlConfigSource(url)

    val config = SmallRyeConfigBuilder()
        .withSources(yaml)
        .withMapping(CapacnanBlueprint::class.java)
        .addDefaultInterceptors()
        .build()

    val blueprint = config.getConfigMapping(CapacnanBlueprint::class.java)


    val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .apply {
            setAnnotationIntrospector(CapacnanBlueprintIntrospector())
        }

    val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(blueprint)

    val outputPath = Paths.get("build/generated/blueprint.json")

    Files.createDirectories(outputPath.parent)

    Files.writeString(outputPath, json)

    println(json)
}