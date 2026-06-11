package it.lysz210.akasha.capacnan.blueprintbuilder

import io.quarkus.test.junit.QuarkusTest
import it.lysz210.akasha.capacnan.blueprint.CapacnanBlueprint
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@QuarkusTest
class InitTest {

    @jakarta.inject.Inject
    private lateinit var blueprint: CapacnanBlueprint

    @Test
    fun `init should work`() {
        assertNotNull(blueprint)
    }

}