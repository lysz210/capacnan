package it.lysz210.akasha.capacnan.blueprint.credentials

import io.smallrye.config.SmallRyeConfigBuilder
import io.smallrye.config.source.yaml.YamlConfigSource
import io.smallrye.config.validator.BeanValidationConfigValidatorImpl
import it.lysz210.akasha.capacnan.blueprint.Blueprint
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.reflect.KClass

class CredentialsBlueprintTest {
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
        val credentialsKlass = CredentialsBlueprint::class
        val config = builder.withSources(yamPropertiesResource(credentialsKlass, "blueprint-credentials.yaml"))
            .withMapping(credentialsKlass.java)
            .build()

        var blueprint: CredentialsBlueprint? = null

        assertDoesNotThrow {
            blueprint = config.getConfigMapping(credentialsKlass.java)
        }
        assertNotNull(blueprint)

    }
}