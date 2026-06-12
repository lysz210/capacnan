package it.lysz210.akasha.capacnan.blueprint.geo

import io.smallrye.config.SmallRyeConfigBuilder
import io.smallrye.config.source.yaml.YamlConfigSource
import io.smallrye.config.validator.BeanValidationConfigValidatorImpl
import it.lysz210.akasha.capacnan.blueprint.Blueprint
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.reflect.KClass

class GeoBlueprintTest {
    fun buidler(): SmallRyeConfigBuilder {

        return SmallRyeConfigBuilder()
            .withValidator(BeanValidationConfigValidatorImpl())
            .addDefaultInterceptors()
            .withSources(yamPropertiesResource(Blueprint::class, "blueprint-root.yaml"))
    }

    fun yamPropertiesResource(klass: KClass<*>, fileName: String): YamlConfigSource {
        val url = klass.java.classLoader.getResource(fileName)
            ?: throw IllegalArgumentException("$fileName not found!")
        return YamlConfigSource(url)
    }

    @Test
    fun `load config`() {
        val builder = buidler()
        val geoKlass = GeoBlueprint::class
        val config = builder.withSources(yamPropertiesResource(geoKlass, "blueprint-geo.yaml"))
            .withMapping(geoKlass.java)
            .build()

        var blueprint: GeoBlueprint? = null

        assertDoesNotThrow {
            blueprint = config.getConfigMapping(geoKlass.java)
        }
        assertNotNull(blueprint)

    }
}