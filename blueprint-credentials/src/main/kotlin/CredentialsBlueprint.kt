package it.lysz210.akasha.capacnan.blueprint.credentials

import io.smallrye.config.ConfigMapping
import it.lysz210.akasha.capacnan.blueprint.Blueprint
import jakarta.validation.constraints.NotNull

@ConfigMapping(prefix = "capacnan")
interface CredentialsBlueprint: Blueprint {

    @NotNull(message = "credentials is required. Check blueprint-credentials.yaml is imported.")
    fun credentials(): Credentials

    interface Credentials :
        Blueprint.Resources,
        Blueprint.WithKeyValue,
        Blueprint.WithStream
}