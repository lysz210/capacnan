package it.lysz210.akasha.capacnan.blueprintbuilder

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import it.lysz210.akasha.capacnan.blueprint.geo.GeoBlueprint
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(NatsTestResource::class)
class InitTest {

    @jakarta.inject.Inject
    private lateinit var blueprintGeoBlueprint: GeoBlueprint

    @Test
    fun `init should work`() {
        assertNotNull(blueprintGeoBlueprint)
    }

}