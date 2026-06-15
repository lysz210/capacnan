package it.lysz210.akasha.capacnan.blueprint.builder

import io.nats.client.Connection
import io.quarkus.logging.Log
import io.quarkus.runtime.ShutdownEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes

@ApplicationScoped
class ShutdownHandler(
    private val natsConnection: Connection,
) {
    fun onShutdown(@Observes event: ShutdownEvent) {
        Log.info("Stopping NATS connection gracefully...")

        try {
            if (natsConnection.status != Connection.Status.CLOSED) {
                // 1. Flush any remaining published messages in the buffer
                natsConnection.flush(java.time.Duration.ofSeconds(2))

                // 2. Close the connection gracefully
                natsConnection.close()
                Log.info("NATS connection closed cleanly.")
            }
        } catch (e: Exception) {
            Log.error("Error during NATS connection shutdown", e)
        }
    }
}