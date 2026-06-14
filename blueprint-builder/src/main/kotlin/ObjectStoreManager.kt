package it.lysz210.akasha.capacnan.blueprint.builder

import io.nats.client.Connection
import io.nats.client.api.ObjectStoreConfiguration
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ObjectStoreManager (
    private val natsConnection: Connection
) {

    fun initObjectStores(
        domain: String,
        config: ObjectStoreConfiguration
    ) {
        val storeName = config.bucketName
        try {
            val osm = natsConnection.objectStoreManagement()

            if (!osm.bucketNames.contains(storeName)) {
                Log.info("Creating $domain ObjectStore $storeName")
                osm.create(config)
            } else {
                Log.info("ObjectStore $storeName already exists for $domain")
            }
        } catch (ex: Exception) {
            Log.error("Error creating ObjectStore for $storeName", ex)
        }
    }

}