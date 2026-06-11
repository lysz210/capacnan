package it.lysz210.akasha.capacnan.blueprint

import io.smallrye.config.ConfigMapping
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

@ConfigMapping(prefix = "capacnan")
interface CapacnanBlueprint {

    @NotBlank(message = "root is required. Check blueprint-root.yaml is imported.")
    fun root(): String

    @NotNull(message = "credentials is required. Check blueprint-credentials.yaml is imported.")
    fun security(): Security

    @NotNull(message = "geo is required. Check blueprint-geo.yaml is imported.")
    fun geo(): Geo

    interface Security :
        Resources,
            WithKeyValue,
            WithStream

    interface Geo :
        Resources,
            WithKeyValue,
            WithObjectStore,
            WithStream

    interface Resources {
        @NotBlank(message = "Resource domain is required.")
        fun domain(): String
        @NotBlank(message = "Resource name prefix is required.")
        fun namePrefix(): String
        @NotBlank(message = "Resource key prefix is required.")
        fun keyPrefix(): String
    }

    interface WithKeyValue {
        @NotNull(message = "KeyValue definition is required.")
        fun kv(): KeyValue
    }

    interface WithStream {
        @NotBlank(message = "Stream name is required.")
        fun stream(): String
        @NotEmpty(message = "Stream subjects prefix is required.")
        fun subjects(): List<@NotBlank(message = "Subject must not be blank") String>
    }

    interface WithObjectStore {
        @NotNull(message = "ObjectStore definition is required.")
        fun objectStore(): ObjectStore
    }

    interface KeyValue {
        @NotBlank(message = "KeyValue bucket name is required.")
        fun bucket(): String
    }

    interface ObjectStore {
        @NotBlank(message = "ObjectStore bucket name is required.")
        fun bucket(): String
    }
}