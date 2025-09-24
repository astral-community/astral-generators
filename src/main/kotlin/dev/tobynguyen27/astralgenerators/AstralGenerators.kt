package dev.tobynguyen27.astralgenerators

import com.tterrag.registrate.Registrate
import dev.tobynguyen27.astralgenerators.data.AGBlockEntities
import dev.tobynguyen27.astralgenerators.data.AGBlocks
import dev.tobynguyen27.astralgenerators.data.AGItems
import dev.tobynguyen27.astralgenerators.data.lang.Texts
import dev.tobynguyen27.astralgenerators.data.recipes.AGRecipes
import dev.tobynguyen27.astralgenerators.gui.AGMenuTypes
import dev.tobynguyen27.astralgenerators.hook.IntegrationHook
import dev.tobynguyen27.astralgenerators.utils.Identifier
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder

object AstralGenerators : ModInitializer {
    const val MOD_ID = "astralgenerators"
    const val MOD_NAME = "Astral Generators"
    val REGISTRATE = Registrate.create(MOD_ID)

    val ITEM_GROUP = FabricItemGroupBuilder.build(Identifier("general")) { AGItems.ASTRALNOMICON.get().defaultInstance }

    override fun onInitialize() {
        initializeItemGroups()
        Texts.init()

        AGItems.init()
        AGBlocks.init()
        AGBlockEntities.init()
        AGMenuTypes.init()
        AGRecipes.init()

        REGISTRATE.register()
        IntegrationHook.init()
    }

    private fun initializeItemGroups() {
        REGISTRATE.creativeModeTab { ITEM_GROUP }
        REGISTRATE.addRawLang("itemGroup.$MOD_ID.general", MOD_NAME)
    }
}
