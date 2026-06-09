package it.lysz210.akasha.capacnan.blueprint

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "capacnan")
interface CapacnanBlueprint {

    fun root(): String

    fun security(): Security

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
        fun domain(): String
        fun namePrefix(): String
        fun keyPrefix(): String
    }

    interface WithKeyValue {
        fun kv(): KeyValue
    }

    interface WithStream {
        fun stream(): String
        fun subjects(): List<String>
    }

    interface WithObjectStore {
        fun objectStore(): ObjectStore
    }

    interface KeyValue {
        fun bucket(): String
    }

    interface ObjectStore {
        fun bucket(): String
    }
}