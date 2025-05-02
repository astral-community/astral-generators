package dev.tobynguyen27.astralgenerators.data.blocks

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.util.entry.BlockEntry
import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.utils.FormattingUtil
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material

object AGCasings {

    val BOILER_CASING: BlockEntry<Block> =
        create("boiler_casing", ::Block).register()
    val STEAM_TURBINE_CASING: BlockEntry<Block> =
        create("steam_turbine_casing", ::Block).register()
    val FUSION_REACTOR_CASING: BlockEntry<Block> =
        create("fusion_reactor_casing", ::Block).register()
    val PIPE_CASING: BlockEntry<Block> =
        create("pipe_casing", ::Block).register()
    val MATRIX_CASING: BlockEntry<Block> = create("matrix_casing", ::Block).register()

    private fun <T : Block> create(
        name: String,
        displayName: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<T, Registrate> {
        return REGISTRATE.block<T>(name, blockSupplier).lang(displayName).blockstate { ctx, prov ->
            prov.simpleBlock(
                ctx.entry,
                prov.models().cubeAll(ctx.name, prov.modLoc("block/casings/${name}"))
            )
        }.properties {
            BlockBehaviour.Properties.of(Material.METAL).strength(5f, 6f).sound(SoundType.METAL)
                .requiresCorrectToolForDrops()

        }.tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL).simpleItem()
    }

    private fun <T : Block> create(
        name: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<T, Registrate> {
        return create(name, FormattingUtil.toEnglishName(name), blockSupplier)
    }

    fun init() {}
}