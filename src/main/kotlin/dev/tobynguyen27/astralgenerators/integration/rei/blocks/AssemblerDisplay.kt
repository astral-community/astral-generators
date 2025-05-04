package dev.tobynguyen27.astralgenerators.integration.rei.blocks

import dev.tobynguyen27.astralgenerators.data.recipes.AssemblerRecipe
import dev.tobynguyen27.astralgenerators.integration.rei.AGREIPlugin
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.basic.BasicDisplay
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.util.EntryIngredients
import net.minecraft.resources.ResourceLocation

class AssemblerDisplay(val id: ResourceLocation, inputs: List<EntryIngredient>, outputs: List<EntryIngredient>) :
    BasicDisplay(inputs, outputs) {


    override fun getCategoryIdentifier(): CategoryIdentifier<*>? {
        return AGREIPlugin.ASSEMBLER
    }

    fun getRecipeId(): ResourceLocation {
        return id
    }

    companion object {
        fun of(recipe: AssemblerRecipe): AssemblerDisplay {
            val inputs = recipe.getInputItems().map { ingredient ->
                EntryIngredients.ofIngredient(ingredient)
            }
            val output = listOf(EntryIngredients.of(recipe.getOutputItem()))
            return AssemblerDisplay(recipe.id, inputs, output)
        }
    }
}
