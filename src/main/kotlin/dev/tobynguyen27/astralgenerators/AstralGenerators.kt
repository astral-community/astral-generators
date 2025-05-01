package dev.tobynguyen27.astralgenerators

import com.tterrag.registrate.Registrate
import dev.tobynguyen27.astralgenerators.data.AGBlockEntities
import dev.tobynguyen27.astralgenerators.data.AGBlocks
import dev.tobynguyen27.astralgenerators.data.AGFluids
import dev.tobynguyen27.astralgenerators.data.AGItems
import dev.tobynguyen27.astralgenerators.data.blockentities.AGBlockEntitiesIntegrations
import dev.tobynguyen27.astralgenerators.data.blockentities.AssemblerEntity
import dev.tobynguyen27.astralgenerators.data.recipes.AGRecipes
import dev.tobynguyen27.astralgenerators.gui.AGMenuTypes
import dev.tobynguyen27.astralgenerators.multiblock.MultiblockManager
import dev.tobynguyen27.astralgenerators.utils.FormattingUtil
import dev.tobynguyen27.astralgenerators.utils.TimeKeeper
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory
import team.reborn.energy.api.EnergyStorage

object AstralGenerators : ModInitializer {
    const val MOD_ID: String = "astralgenerators"
    const val MOD_NAME: String = "Astral Generators"

    val LOGGER = LoggerFactory.getLogger(MOD_ID)
    val REGISTRATE = Registrate.create(MOD_ID)
    val ITEM_GROUP = FabricItemGroupBuilder.build(id("general")) {
        AGItems.ASTRALNOMICON.get().defaultInstance
    }

    override fun onInitialize() {
        LOGGER.info("Initializing $MOD_NAME...")
        initializeItemGroups()

        AGBlockEntities.init()
        AGItems.init()
        AGBlocks.init()
        AGFluids.init()
        AGMenuTypes.init()
        AGRecipes.init()

        REGISTRATE.register()
        MultiblockManager.init()
        AGBlockEntitiesIntegrations.init()
        ServerTickEvents.END_SERVER_TICK.register { TimeKeeper.incrementServerTick() }
    }

    private fun initializeItemGroups() {
        LOGGER.info("Registering item groups...")

        REGISTRATE.creativeModeTab { ITEM_GROUP }
        REGISTRATE.addRawLang("itemGroup.$MOD_ID.general", MOD_NAME)
    }

    fun id(path: String): ResourceLocation {
        return ResourceLocation(MOD_ID, FormattingUtil.toLowerCaseUnder(path))
    }

    fun appendIdString(id: String): String {
        return if (id.indexOf(":") == -1) {
            "$MOD_ID:$id"
        } else {
            id
        }
    }

    fun isModLoaded(modId: String): Boolean {
        return FabricLoader.getInstance().isModLoaded(modId)
    }
}