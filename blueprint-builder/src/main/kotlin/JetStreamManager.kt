package it.lysz210.akasha.capacnan.blueprint.builder

import io.nats.client.Connection
import io.nats.client.api.StreamConfiguration
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class JetStreamManager(

    private val natsConnection: Connection,
) {
    fun initJetStream(
        domain: String,
        config: StreamConfiguration,
    ) {
        val streamName = config.name

        try {
            val manager = natsConnection.jetStreamManagement()
            if (manager.streamNames.contains(streamName)) {
                Log.info("Updating $domain Stream $streamName")
                manager.updateStream(config)
            } else {
                Log.info("Creating $domain Stream $streamName")
                manager.addStream(config)
            }

        } catch (e: Exception) {
            Log.error("Failed to initialize JetStream $streamName for $domain.", e)
        }

    }
}