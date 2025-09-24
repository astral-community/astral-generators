package dev.tobynguyen27.astralgenerators.data.registries

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateBlockstateProvider
import com.tterrag.registrate.util.nullness.NonNullFunction
import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.data.machines.assembler.Assembler
import dev.tobynguyen27.astralgenerators.utils.StringHelper
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty

object MachineRegistry {
    fun <T : Block> registerSimpleMachine(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, Registrate> =
        AstralGenerators.REGISTRATE.block<T>(name, factory)
            .lang(StringHelper.toEnglishName(name))
            .blockstate { ctx, prov ->
                for ((direction, rotationY) in
                    mapOf(
                        Direction.NORTH to 0,
                        Direction.SOUTH to 180,
                        Direction.EAST to 90,
                        Direction.WEST to 270,
                    )) {
                    createMachineModelWithTopActive(ctx, prov, true, direction, rotationY, Assembler.ACTIVE, Assembler.FACING)
                    createMachineModelWithTopActive(ctx, prov, false, direction, rotationY, Assembler.ACTIVE, Assembler.FACING)
                }
            }
            .simpleItem()

    private fun <T : Block> createMachineModelWithTopActive(
        ctx: DataGenContext<Block, T>,
        prov: RegistrateBlockstateProvider,
        active: Boolean,
        direction: Direction,
        rotationY: Int,
        activeProp: BooleanProperty,
        directionProp: DirectionProperty,
    ) {
        val modelName = if (active) "${ctx.name}_active" else ctx.name
        val sideTexture = prov.modLoc("block/machines/side")
        val frontTexture =
            prov.modLoc(
                if (active) "block/machines/${ctx.name}_active" else "block/machines/${ctx.name}",
            )
        val topTexture =
            prov.modLoc(
                if (active) "block/machines/${ctx.name}_top_active" else "block/machines/${ctx.name}_top",
            )

        prov
            .getVariantBuilder(ctx.entry)
            .partialState()
            .with(activeProp, active)
            .with(directionProp, direction)
            .modelForState()
            .modelFile(prov.models().orientableWithBottom(modelName, sideTexture, frontTexture, sideTexture, topTexture))
            .rotationY(rotationY)
            .addModel()
    }
}
