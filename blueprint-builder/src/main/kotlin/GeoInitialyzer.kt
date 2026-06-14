package it.lysz210.akasha.capacnan.blueprint.builder

import io.nats.client.api.*
import io.quarkus.runtime.StartupEvent
import it.lysz210.akasha.capacnan.blueprint.geo.GeoBlueprint
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes

@ApplicationScoped
class GeoInitialyzer(
    blueprint: GeoBlueprint,
) {
    val geoResource: GeoBlueprint.Geo = blueprint.geo()

    fun onStratup(
        @Observes ev: StartupEvent,
        objectStoreManager: ObjectStoreManager,
        keyValueManager: KeyValueManager,
        streamManager: JetStreamManager,
    ) {
        val domain = geoResource.domain()

        val objectStoreConfig = ObjectStoreConfiguration.builder()
            .name(geoResource.objectStore().bucket())
            .description("$domain ObjectStore")
            .storageType(StorageType.File)
            .build()

        objectStoreManager.initObjectStores(domain, objectStoreConfig)

        val streamConfig = StreamConfiguration.builder()
            .name(geoResource.stream())
            .description("$domain stream")
            .subjects(geoResource.subjects())
            .storageType(StorageType.File)
            .retentionPolicy(RetentionPolicy.Limits)
            .build()

        streamManager.initJetStream(domain, streamConfig)

        val kvConfig = KeyValueConfiguration.builder()
            .name(geoResource.kv().bucket())
            .description("Geo KeyValue bucket")
            .storageType(StorageType.File)
            .build()

        keyValueManager.initKeyValue(domain, kvConfig)
    }
}