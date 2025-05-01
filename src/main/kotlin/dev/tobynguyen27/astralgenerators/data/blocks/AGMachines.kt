package dev.tobynguyen27.astralgenerators.data.blocks

import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateBlockstateProvider
import com.tterrag.registrate.util.entry.BlockEntry
import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.data.blockentities.AssemblerEntity
import dev.tobynguyen27.astralgenerators.data.blockentities.MultiblockProjectorEntity
import dev.tobynguyen27.astralgenerators.data.blocks.machines.*
import dev.tobynguyen27.astralgenerators.utils.AGDirections
import dev.tobynguyen27.astralgenerators.utils.FormattingUtil
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import java.util.function.Supplier

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

    val MULTIBLOCK_PROJECTOR: BlockEntry<MultiblockProjector> =
        REGISTRATE.block<MultiblockProjector>(MultiblockProjector.ID, ::MultiblockProjector)
            .lang(FormattingUtil.toEnglishName(MultiblockProjector.ID)).blockEntity { type, pos, state ->
                MultiblockProjectorEntity(type, pos, state)
            }.build().blockstate { ctx, prov ->
                for ((direction, rotationY) in AGDirections.directions) {
                    prov.getVariantBuilder(ctx.entry).partialState()
                        .with(MultiblockProjector.FACING, direction)
                        .modelForState()
                        .modelFile(
                            prov.models().orientable(
                                ctx.name,
                                prov.modLoc("block/casings/machine/side"),
                                prov.modLoc("block/machines/multiblock_projector"), // Front
                                prov.modLoc("block/casings/machine/side")
                            )
                        )
                        .rotationY(rotationY)
                        .addModel()
                }
            }.simpleItem().register()

    val BOILER_CONTROLLER =
        REGISTRATE.block<BoilerController>(BoilerController.ID, ::BoilerController)
            .lang(FormattingUtil.toEnglishName(BoilerController.ID))
            .blockstate { ctx, prov ->
                for ((direction, rotationY) in AGDirections.directions) {
                    createControllerModel("boiler_casing", ctx, prov, true, direction, rotationY)
                    createControllerModel("boiler_casing", ctx, prov, false, direction, rotationY)
                }
            }.simpleItem().addLayer { Supplier { RenderType.translucent() } }.register()
    val STEAM_TURBINE_CONTROLLER =
        REGISTRATE.block<SteamTurbineController>(SteamTurbineController.ID, ::SteamTurbineController)
            .lang(FormattingUtil.toEnglishName(SteamTurbineController.ID))
            .blockstate { ctx, prov ->
                for ((direction, rotationY) in AGDirections.directions) {
                    createControllerModel("steam_turbine_casing", ctx, prov, true, direction, rotationY)
                    createControllerModel("steam_turbine_casing", ctx, prov, false, direction, rotationY)
                }
            }.simpleItem().addLayer { Supplier { RenderType.translucent() } }.register()

    val FUSION_REACTOR_CONTROLLER =
        REGISTRATE.block<FusionReactorController>(FusionReactorController.ID, ::FusionReactorController)
            .lang(FormattingUtil.toEnglishName(FusionReactorController.ID))
            .blockstate { ctx, prov ->
                for ((direction, rotationY) in AGDirections.directions) {
                    createControllerModel("fusion_reactor_casing", ctx, prov, true, direction, rotationY)
                    createControllerModel("fusion_reactor_casing", ctx, prov, false, direction, rotationY)
                }
            }.simpleItem().addLayer { Supplier { RenderType.translucent() } }.register()

    private fun <T : Block> createControllerModel(
        casingName: String,
        ctx: DataGenContext<Block, T>,
        prov: RegistrateBlockstateProvider,
        active: Boolean,
        direction: Direction,
        rotationY: Int
    ) {
        val modelName = if (active) "${ctx.name}_active" else ctx.name
        val sideTexture = prov.modLoc("block/casings/${casingName}")
        val frontTexture = prov.modLoc(
            if (active) "block/machines/${ctx.name}_active" else "block/machines/${ctx.name}"
        )

        prov.getVariantBuilder(ctx.entry).partialState()
            .with(Assembler.ACTIVE, active)
            .with(Assembler.FACING, direction)
            .modelForState()
            .modelFile(
                prov.models().withExistingParent(modelName, "astralgenerators:block/cube_2_layer/default")
                    .texture("particle", sideTexture)
                    .texture("bot_down", sideTexture)
                    .texture("bot_up", sideTexture)
                    .texture("bot_east", sideTexture)
                    .texture("bot_west", sideTexture)
                    .texture("bot_south", sideTexture)
                    .texture("bot_north", sideTexture)
                    .texture("top_down", sideTexture)
                    .texture("top_up", sideTexture)
                    .texture("top_east", sideTexture)
                    .texture("top_west", sideTexture)
                    .texture("top_south", sideTexture)
                    .texture("top_north", frontTexture)
            )
            .rotationY(rotationY)
            .addModel()
    }

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