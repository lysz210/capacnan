package it.lysz210.akasha.capacnan.blueprint.builder

import io.nats.client.Connection
import io.nats.client.api.KeyValueConfiguration
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class KeyValueManager(
    private val natsConnection: Connection,
) {
    fun initKeyValue(
        domain: String,
        config: KeyValueConfiguration
    ) {
        val bucketName = config.bucketName
        try {
            val manager = natsConnection.keyValueManagement()
            if (manager.bucketNames.contains(bucketName)) {
                Log.info("Updating $domain KeyValue $bucketName")
                manager.update(config)
            } else {
                Log.info("Creating $domain KeyValue $bucketName")
                manager.create(config)
            }

        } catch (e: Exception) {
            Log.error("Failed to initialize KeyValue $bucketName for $domain.", e)
        }

    }
}