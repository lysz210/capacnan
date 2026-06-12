package it.lysz210.akasha.capacnan.blueprint

import io.smallrye.config.SmallRyeConfigBuilder
import io.smallrye.config.source.yaml.YamlConfigSource
import io.smallrye.config.validator.BeanValidationConfigValidatorImpl
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.reflect.KClass

class BlueprintTest {
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
        val klass = Blueprint::class
        val config = builder
            .withMapping(klass.java, "capacnan")
            .build()

        var blueprint: Blueprint? = null

        assertDoesNotThrow {
            blueprint = config.getConfigMapping(klass.java, "capacnan")
        }
        assertNotNull(blueprint)

    }
}