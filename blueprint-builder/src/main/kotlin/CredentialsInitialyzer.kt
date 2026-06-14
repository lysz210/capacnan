package it.lysz210.akasha.capacnan.blueprint.builder

import io.nats.client.api.KeyValueConfiguration
import io.nats.client.api.RetentionPolicy
import io.nats.client.api.StorageType
import io.nats.client.api.StreamConfiguration
import io.quarkus.runtime.StartupEvent
import it.lysz210.akasha.capacnan.blueprint.credentials.CredentialsBlueprint
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes

@ApplicationScoped
class CredentialsInitialyzer(
    blueprint: CredentialsBlueprint
) {
    val credentialsResource: CredentialsBlueprint.Credentials = blueprint.credentials()

    fun onStratup(
        @Observes ev: StartupEvent,
        keyValueManager: KeyValueManager,
        streamManager: JetStreamManager,
    ) {
        val domain = credentialsResource.domain()

        val streamConfig = StreamConfiguration.builder()
            .name(credentialsResource.stream())
            .description("$domain stream")
            .subjects(credentialsResource.subjects())
            .storageType(StorageType.File)
            .retentionPolicy(RetentionPolicy.Limits)
            .build()

        streamManager.initJetStream(domain, streamConfig)

        val kvConfig = KeyValueConfiguration.builder()
            .name(credentialsResource.kv().bucket())
            .description("Geo KeyValue bucket")
            .storageType(StorageType.File)
            .build()

        keyValueManager.initKeyValue(domain, kvConfig)
    }
}