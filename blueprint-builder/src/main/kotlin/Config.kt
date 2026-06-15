package it.lysz210.akasha.capacnan.blueprint.builder

import io.nats.client.Connection
import io.nats.client.Nats
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class Config {

    @Produces
    @ApplicationScoped
    fun natConnection(@ConfigProperty(name = "nats.connection.url") natsUrl: String): Connection {
        return Nats.connect(natsUrl)
    }
}