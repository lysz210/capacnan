package it.lysz210.akasha.capacnan.blueprint

import io.smallrye.config.SmallRyeConfigBuilder
import io.smallrye.config.source.yaml.YamlConfigSource
import kotlin.reflect.KClass

class ConfigMapperFactory {

    fun buidler(): SmallRyeConfigBuilder {

        return SmallRyeConfigBuilder()
            .withSources(yamPropertiesResource(Blueprint::class, "blueprint-root.yaml"))
    }

    fun yamPropertiesResource(klass: KClass<*>, fileName: String): YamlConfigSource {
        val url = klass.java.classLoader.getResource(fileName)
            ?: throw IllegalArgumentException("$fileName not found!")
        return YamlConfigSource(url)
    }
}