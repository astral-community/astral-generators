package dev.tobynguyen27.astralgenerators.data.blocks

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.data.blocks.vents.SteamTurbineVent
import dev.tobynguyen27.astralgenerators.utils.FormattingUtil
import net.minecraft.client.renderer.RenderType
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor
import java.util.function.Supplier

object AGVents {
    val STEAM_TURBINE_VENT =
        create(
            "steam_turbine_vent",
            "steam_turbine_casing",
            ::SteamTurbineVent
        ).register()

    private fun <T : Block> create(
        name: String,
        casingName: String,
        displayName: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<T, Registrate> {
        return REGISTRATE.block<T>(name, blockSupplier).lang(displayName)
            .properties {
                BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(1f, 1f)
                    .sound(SoundType.METAL)
            }.blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    prov.models().withExistingParent(ctx.name, prov.modLoc("block/cube_2_layer/all"))
                        .texture("bot_all", prov.modLoc("block/casings/${casingName}"))
                        .texture(
                            "top_all",
                            prov.modLoc("block/vents/${name}")
                        )
                )
            }.simpleItem().addLayer { Supplier { RenderType.translucent() } }
    }

    private fun <T : Block> create(
        name: String,
        casingName: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<T, Registrate> {
        return create(name, casingName, FormattingUtil.toEnglishName(name), blockSupplier)
    }

    fun init() {
        LOGGER.info("Registering vent blocks...")
    }
}