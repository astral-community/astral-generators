package dev.tobynguyen27.astralgenerators.data

import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.data.tags.FluidTags

object AGFluids {
    val STEAM =
        REGISTRATE.fluid("steam", AstralGenerators.id("fluid/steam"), AstralGenerators.id("fluid/steam_flow"))
            .noBucket().tag(FluidTags.STEAM_TAG).register()

    fun init() {
        LOGGER.info("Registering fluids...")
    }
}