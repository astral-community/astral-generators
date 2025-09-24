package dev.tobynguyen27.astralgenerators.data.recipes

import dev.tobynguyen27.astralgenerators.utils.Identifier
import net.minecraft.core.Registry

object AGRecipes {

    fun init() {
        Registry.register(Registry.RECIPE_SERIALIZER,
            Identifier("assembler"),
        AssemblerRecipe.Serializer)

        Registry.register(
            Registry.RECIPE_TYPE,
            Identifier(AssemblerRecipe.Type.ID),
            AssemblerRecipe.Type
        )
    }
}
