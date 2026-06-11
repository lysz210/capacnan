package it.lysz210.akasha.capacnan.blueprint.geo

import io.smallrye.config.ConfigMapping
import it.lysz210.akasha.capacnan.blueprint.Blueprint
import jakarta.validation.constraints.NotNull

@ConfigMapping(prefix = "capacnan")
interface GeoBlueprint : Blueprint {

    @NotNull(message = "geo is required. Check blueprint-geo.yaml is imported.")
    fun geo(): Geo

    interface Geo :
        Blueprint.Resources,
        Blueprint.WithKeyValue,
        Blueprint.WithObjectStore,
        Blueprint.WithStream
}