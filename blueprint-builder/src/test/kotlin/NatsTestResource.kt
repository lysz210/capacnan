package it.lysz210.akasha.capacnan.blueprint.builder

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class NatsTestResource : QuarkusTestResourceLifecycleManager {

    private var natsContainer: GenericContainer<*>? = null

    override fun start(): Map<String, String> {
        // Initialize and start the container using Kotlin's scoping functions
        natsContainer = GenericContainer(DockerImageName.parse("nats:2.10-alpine")).apply {
            withExposedPorts(4222)
            withCommand("-js")
            start()
        }

        // Retrieve the dynamic host and assigned random port safely
        val host = natsContainer?.host ?: "localhost"
        val mappedPort = natsContainer?.getMappedPort(4222) ?: 4222
        val connectionString = "nats://$host:$mappedPort"

        // Return the configuration override for Quarkus
        return mapOf(
            "quarkus.messaging.nats.connection.servers" to connectionString
        )
    }

    override fun stop() {
        // Safe call to stop the container if it was initialized
        natsContainer?.stop()
    }
}