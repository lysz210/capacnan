package it.lysz210.akasha.capacnan.blueprint.builder

import io.nats.client.Connection
import io.nats.client.api.ObjectStoreConfiguration
import io.nats.client.api.StorageType
import io.quarkus.logging.Log
import io.quarkus.runtime.StartupEvent
import it.lysz210.akasha.capacnan.blueprint.credentials.CredentialsBlueprint
import it.lysz210.akasha.capacnan.blueprint.geo.GeoBlueprint
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes

@ApplicationScoped
class ObjectStoreInitializer(
    private val geoBlueprint: GeoBlueprint,
    private val credentialsBlueprint: CredentialsBlueprint
) {

    fun initObjectStores(
        @Observes _ev: StartupEvent,
        natsConnection: Connection
    ) {
        Log.info("Initializing object stores for ${geoBlueprint.root()}")

        val storeName = geoBlueprint.geo()
            .objectStore().bucket()

        try {
            val osm = natsConnection.objectStoreManagement()

            if (!osm.bucketNames.contains(storeName)) {
                Log.info("Creating object-store for $storeName")

                val config = ObjectStoreConfiguration.builder()
                    .name(storeName)
                    .storageType(StorageType.File)
                    .build()
                osm.create(config)
            } else {
                Log.info("object-store $storeName already exists")
            }
        } catch (ex: Exception) {
            Log.error("Error creating object-store for $storeName", ex)
        }
    }

}