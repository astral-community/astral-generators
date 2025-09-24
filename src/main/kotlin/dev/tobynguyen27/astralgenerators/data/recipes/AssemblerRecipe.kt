package dev.tobynguyen27.astralgenerators.data.recipes

import com.google.gson.JsonObject
import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.data.recipes.base.FluidInput
import dev.tobynguyen27.astralgenerators.data.recipes.base.ItemInput
import dev.tobynguyen27.astralgenerators.data.recipes.base.ItemOutput
import dev.tobynguyen27.astralgenerators.utils.AGInventory
import net.minecraft.core.Registry
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.BannerDuplicateRecipe
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

class AssemblerRecipe(
    val recipeId: ResourceLocation,
    val energy: Int,
    val duration: Int,
    val itemInputs: List<ItemInput>,
    val fluidInputs: List<FluidInput>,
    val itemOutput: ItemOutput
) : Recipe<AGInventory> {
    override fun matches(container: AGInventory, level: Level): Boolean {
        return false
    }

    override fun assemble(container: AGInventory): ItemStack {
        return ItemStack(itemOutput.item, itemOutput.amount)
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean {
        return true
    }

    override fun getResultItem(): ItemStack {
        return ItemStack(itemOutput.item, itemOutput.amount)
    }

    override fun getId(): ResourceLocation {
        return recipeId
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return Serializer
    }

    override fun getType(): RecipeType<*> {
        return Type
    }

    object Type : RecipeType<AssemblerRecipe> {
        val ID = "assembler"
    }

    object Serializer : RecipeSerializer<AssemblerRecipe> {
        override fun fromJson(recipeId: ResourceLocation, json: JsonObject): AssemblerRecipe {

            val energy = GsonHelper.getAsInt(json, "energy", 0)
            val duration = GsonHelper.getAsInt(json, "duration", 20)

            val itemInput = GsonHelper.getAsJsonArray(json, "item_inputs").map { it ->
                val amount = GsonHelper.getAsInt(it.asJsonObject, "amount", 1)

                ItemInput(Ingredient.fromJson(it), amount)
            }

            val fluidInput = GsonHelper.getAsJsonArray(json, "fluid_inputs").map { it ->
                val amount = GsonHelper.getAsInt(it.asJsonObject, "amount", 1000)
                FluidInput(Registry.FLUID.get(readIdentifier(it.asJsonObject, "fluid")), amount)
            }

            val output = GsonHelper.getAsJsonObject(json, "output")

            return AssemblerRecipe(recipeId, energy, duration, itemInput, fluidInput, ItemOutput(
                Ingredient.fromJson(output).items[0].item,
                GsonHelper.getAsInt(output, "amount", 1)
            ))
        }

        override fun fromNetwork(recipeId: ResourceLocation, buffer: FriendlyByteBuf): AssemblerRecipe {
            val energy = buffer.readVarInt()
            val duration = buffer.readVarInt()

            val itemInputs = mutableListOf<ItemInput>()
            while (buffer.isReadable) {
                val amount = buffer.readVarInt()
                val ingredient = Ingredient.fromNetwork(buffer)
                itemInputs.add(ItemInput(ingredient, amount))
            }

            val fluidInputs = mutableListOf<FluidInput>()
            while (buffer.isReadable) {
                val fluidId = buffer.readResourceLocation()
                val amount = buffer.readVarInt()
                val fluid = Registry.FLUID.get(fluidId)
                fluidInputs.add(FluidInput(fluid, amount))
            }

            val itemOutput = buffer.readItem()

            return AssemblerRecipe(
                recipeId,
                energy,
                duration,
                itemInputs,
                fluidInputs,
                ItemOutput(itemOutput.item, itemOutput.count)
            )
        }

        override fun toNetwork(buffer: FriendlyByteBuf, recipe: AssemblerRecipe) {
            buffer.writeVarInt(recipe.energy)
            buffer.writeVarInt(recipe.duration)

            recipe.itemInputs.forEach { (ingredient, amount) ->
                buffer.writeVarInt(amount)
                ingredient.toNetwork(buffer)
            }

            recipe.fluidInputs.forEach { fluidInput ->
                buffer.writeResourceLocation(fluidInput.fluid.registryName)
                buffer.writeVarInt(fluidInput.amount)
            }

            buffer.writeItem(ItemStack(recipe.itemOutput.item, recipe.itemOutput.amount))
        }

        private fun readIdentifier(json: JsonObject, element: String): ResourceLocation {
            val str = GsonHelper.getAsString(json, element).split(":")

            return ResourceLocation(str[0], str[1])
        }
    }
}
