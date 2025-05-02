package dev.tobynguyen27.astralgenerators.data.fluids

import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.data.tags.FluidTags
import dev.tobynguyen27.astralgenerators.utils.Identifier

object AGFluids {
    val STEAM =
        REGISTRATE.fluid("steam", Identifier("fluid/steam"), Identifier("fluid/steam_flow"))
            .noBucket().tag(FluidTags.STEAM_TAG).register()

    fun init() {
        LOGGER.info("Registering fluids...")
    }
}