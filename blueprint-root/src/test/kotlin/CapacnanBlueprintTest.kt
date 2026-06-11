package it.lysz210.akasha.capacnan.blueprint

import io.smallrye.config.SmallRyeConfigBuilder
import io.smallrye.config.source.yaml.YamlConfigSource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CapacnanBlueprintTest {

    @Test
    fun `should load properties`() {
        // 1. Arrange: Prepare the configuration source
        val url = javaClass.classLoader.getResource("blueprint.yaml")
            ?: throw IllegalArgumentException("blueprint.yaml not found!")
        val yaml = YamlConfigSource(url)

        val config = SmallRyeConfigBuilder()
            .withSources(yaml)
            .withMapping(CapacnanBlueprint::class.java)
            .build()

        // 2. Act: Extract the mapping (and capture the returned value cleanly)
        lateinit var blueprint: CapacnanBlueprint
        assertDoesNotThrow {
            blueprint = config.getConfigMapping(CapacnanBlueprint::class.java)
        }

        // 3. Assert: Verify the structural mapping succeeded
        assertNotNull(blueprint.security(), "Security configuration should be mapped")
        assertNotNull(blueprint.geo(), "Geo configuration should be mapped")
    }
}