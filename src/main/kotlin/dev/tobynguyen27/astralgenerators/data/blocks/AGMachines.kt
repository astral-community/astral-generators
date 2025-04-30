package dev.tobynguyen27.astralgenerators.data.blocks

import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateBlockstateProvider
import com.tterrag.registrate.util.entry.BlockEntry
import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.data.blockentities.AssemblerEntity
import dev.tobynguyen27.astralgenerators.data.blocks.machines.Assembler
import dev.tobynguyen27.astralgenerators.utils.AGDirections
import dev.tobynguyen27.astralgenerators.utils.FormattingUtil
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty

object AGMachines {
    val ASSSEMBLER: BlockEntry<Assembler> =
        REGISTRATE.block<Assembler>(Assembler.ID, ::Assembler).lang(FormattingUtil.toEnglishName(Assembler.ID))
            .blockEntity { type, pos, state ->
                AssemblerEntity(type, pos, state)
            }.build().blockstate { ctx, prov ->
                for ((direction, rotationY) in AGDirections.directions) {
                    createModelWithTopActive(ctx, prov, true, direction, rotationY, Assembler.ACTIVE, Assembler.FACING)
                    createModelWithTopActive(ctx, prov, false, direction, rotationY, Assembler.ACTIVE, Assembler.FACING)
                }
            }.simpleItem().register()

    private fun <T : Block> createModelWithTopActive(
        ctx: DataGenContext<Block, T>,
        prov: RegistrateBlockstateProvider,
        active: Boolean,
        direction: Direction,
        rotationY: Int,
        activeProp: BooleanProperty,
        directionProp: DirectionProperty
    ) {
        val modelName = if (active) "${ctx.name}_active" else ctx.name
        val sideTexture = prov.modLoc("block/casings/machine/side")
        val frontTexture = prov.modLoc(
            if (active) "block/machines/${ctx.name}_active" else "block/machines/${ctx.name}"
        )
        val topTexture = prov.modLoc(
            if (active) "block/machines/${ctx.name}_top_active" else "block/machines/${ctx.name}_top"
        )

        prov.getVariantBuilder(ctx.entry).partialState()
            .with(activeProp, active)
            .with(directionProp, direction)
            .modelForState()
            .modelFile(prov.models().orientable(modelName, sideTexture, frontTexture, topTexture))
            .rotationY(rotationY)
            .addModel()
    }

    fun init() {
        LOGGER.info("Registering machines...")
    }

}