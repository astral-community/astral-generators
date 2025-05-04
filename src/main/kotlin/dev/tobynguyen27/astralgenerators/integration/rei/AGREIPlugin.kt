package dev.tobynguyen27.astralgenerators.integration.rei

import dev.tobynguyen27.astralgenerators.data.blocks.AGMachines
import dev.tobynguyen27.astralgenerators.data.recipes.AssemblerRecipe
import dev.tobynguyen27.astralgenerators.integration.rei.blocks.AssemblerCategory
import dev.tobynguyen27.astralgenerators.integration.rei.blocks.AssemblerDisplay
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.client.Minecraft

class AGREIPlugin : REIClientPlugin {
    companion object {
        val ASSEMBLER = CategoryIdentifier.of<AssemblerDisplay>(AGMachines.ASSEMBLER.id.path)
    }

    override fun registerCategories(registry: CategoryRegistry) {
        registry.run {
            add(AssemblerCategory())
            addWorkstations(ASSEMBLER, EntryStacks.of(AGMachines.ASSEMBLER.get()))
        }
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        val recipeManager = Minecraft.getInstance().level!!.recipeManager

        val assemblerRecipes = recipeManager.getAllRecipesFor(AssemblerRecipe.Type.INSTANCE).map(AssemblerDisplay::of)
        assemblerRecipes.forEach { registry.add(it) }
    }

    override fun registerScreens(registry: ScreenRegistry?) {
        super.registerScreens(registry)
    }
}
