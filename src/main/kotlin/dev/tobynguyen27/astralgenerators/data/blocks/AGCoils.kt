package dev.tobynguyen27.astralgenerators.data.blocks

import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.utils.FormattingUtil
import net.minecraft.world.level.material.MaterialColor

object AGCoils {

    val FUSION_REACTOR_COIL =
        create("fusion_reactor_coil", ::Block).register()
    val HIGH_MAGNETIC_COIL = create("high_magnetic_coil", ::Block).register()

    private fun <T : Block> create(
        name: String,
        displayName: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<T, Registrate> {
        return AstralGenerators.REGISTRATE.block<T>(name, blockSupplier).lang(displayName).blockstate { ctx, prov ->
            prov.simpleBlock(
                ctx.entry,
                prov.models().cubeAll(ctx.name, prov.modLoc("block/coils/${name}"))
            )
        }.properties {
            BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(5f, 6f).sound(SoundType.METAL)
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
