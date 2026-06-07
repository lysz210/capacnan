package it.lysz210.akasha.capacnan.blueprint

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "capacnan")
interface CapacnanBlueprint {

    fun root(): String

    fun security(): Security

    fun geo(): Geo

    interface Security : Resources {
        fun kv(): KeyValue
        fun stream(): String
    }

    interface Geo : Resources {
        fun objectStore(): ObjectStore
        fun stream(): String
        fun subjects(): List<String>
    }

    interface Resources {
        fun domain(): String
        fun namePrefix(): String
        fun keyPrefix(): String
    }

    interface KeyValue {
        fun bucket(): String
    }

    interface ObjectStore {
        fun bucket(): String
    }
}