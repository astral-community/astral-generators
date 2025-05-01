package dev.tobynguyen27.astralgenerators.data

import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE

object AGFluids {
    val STEAM =
        REGISTRATE.fluid("steam", AstralGenerators.id("fluid/steam"), AstralGenerators.id("fluid/steam_flow"))
            .noBucket().register()

    fun init() {
        LOGGER.info("Registering fluids...")
    }
}