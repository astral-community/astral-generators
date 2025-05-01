package dev.tobynguyen27.astralgenerators.data.recipes

import com.google.common.collect.HashMultiset
import com.google.gson.JsonObject
import dev.tobynguyen27.astralgenerators.utils.AGContainer
import net.minecraft.core.NonNullList
import net.minecraft.nbt.Tag
import net.minecraft.nbt.TagParser
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class AssemblerRecipe(val recipeId: ResourceLocation, val output: ItemStack, val inputs: NonNullList<Ingredient>) :
    Recipe<AGContainer> {


    override fun matches(container: AGContainer, level: Level): Boolean {
        val stackedContents: StackedContents = StackedContents()
        var i = 0

        for (index in 0..8) {
            val stack = container.getItem(index)

            if (!stack.isEmpty) {
                ++i;
                stackedContents.accountStack(stack, container.getItem(index).count)
            }
        }

        return i == inputs.size && stackedContents.canCraft(this, null)
    }

    override fun assemble(container: AGContainer): ItemStack {
        return output.copy()
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean {
        return true
    }

    override fun getResultItem(): ItemStack {
        return output.copy()
    }

    override fun getId(): ResourceLocation {
        return recipeId
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return Serializer.INSTANCE
    }

    override fun getType(): RecipeType<*> {
        return Type.INSTANCE
    }

    fun getOutputItem(): ItemStack {
        return output
    }

    fun getInputItems(): NonNullList<Ingredient> {
        return inputs
    }

    class Type private constructor() : RecipeType<AssemblerRecipe> {
        companion object {
            val INSTANCE: Type = Type()
            const val ID = "assembler"
        }
    }

    object Serializer : RecipeSerializer<AssemblerRecipe> {
        override fun fromJson(recipeId: ResourceLocation, json: JsonObject): AssemblerRecipe {
            // OUTPUT
            val outputObj = GsonHelper.getAsJsonObject(json, "output")
            val output = ShapedRecipe.itemStackFromJson(outputObj)

            val tag = if (outputObj.has("nbt")) {
                val nbtObj = outputObj.get("nbt")

                if (nbtObj.isJsonObject) {
                    TagParser.parseTag(nbtObj.toString())
                } else {
                    TagParser.parseTag(GsonHelper.convertToString(nbtObj, "nbt"))
                }
            } else {
                null
            }
            if (tag != null) {
                output.tag = tag
            }

            // INPUT
            val inputsArray = GsonHelper.getAsJsonArray(json, "inputs")
            val inputsList = NonNullList.create<Ingredient>()

            inputsArray.forEach { input ->
                val ingredient = Ingredient.fromJson(input)
                val count = GsonHelper.getAsInt(input.asJsonObject, "count", 1)

                if (!ingredient.isEmpty) {
                    repeat(count) {
                        inputsList.add(ingredient)
                    }
                }
            }

            return AssemblerRecipe(recipeId, output, inputsList)
        }

        override fun fromNetwork(recipeId: ResourceLocation, buffer: FriendlyByteBuf): AssemblerRecipe {
            val inputCount = buffer.readInt()
            val inputs = NonNullList.withSize(inputCount, Ingredient.EMPTY)
            repeat(inputCount) { inputs[it] = Ingredient.fromNetwork(buffer) }
            val output = buffer.readItem()

            return AssemblerRecipe(recipeId, output, inputs)
        }

        override fun toNetwork(buffer: FriendlyByteBuf, recipe: AssemblerRecipe) {
            buffer.writeInt(recipe.inputs.size)
            recipe.inputs.forEach { it.toNetwork(buffer) }
            buffer.writeItem(recipe.output)
        }

        val INSTANCE = this
        const val ID = "assembler"
    }
}