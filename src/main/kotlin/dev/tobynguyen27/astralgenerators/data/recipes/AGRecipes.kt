package dev.tobynguyen27.astralgenerators.data.recipes

import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import net.minecraft.core.Registry

object AGRecipes {
    fun init() {
        Registry.register(
            Registry.RECIPE_SERIALIZER,
            AstralGenerators.id(AssemblerRecipe.Serializer.ID),
            AssemblerRecipe.Serializer.INSTANCE
        )

        Registry.register(
            Registry.RECIPE_TYPE,
            AstralGenerators.id(AssemblerRecipe.Type.ID),
            AssemblerRecipe.Type.INSTANCE
        )

        LOGGER.info("Registering recipes...")
    }
}