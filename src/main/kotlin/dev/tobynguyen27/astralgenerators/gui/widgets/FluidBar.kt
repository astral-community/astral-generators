package dev.tobynguyen27.astralgenerators.gui.widgets

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.astralgenerators.data.lang.Texts
import dev.tobynguyen27.astralgenerators.utils.Identifier
import dev.tobynguyen27.astralgenerators.utils.MathHelper.calculateFormattedPercentage
import dev.tobynguyen27.astralgenerators.utils.StringHelper
import dev.tobynguyen27.astralgenerators.utils.StringHelper.toReadableNumberString
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WBar
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent

/**
 * @param fluidVariant The fluid variant to display in the bar.
 * @param currentValue The current energy amount (use PropertyDelegate index).
 * @param maxCapacity The maximum energy capacity (use PropertyDelegate index).
 */
class FluidBar(
    val fluidVariant: () -> FluidVariant,
    val currentValue: () -> Int,
    val maxCapacity: () -> Int,
) :
    WBar(
        Identifier("textures/gui/widgets/widget_fluid_empty.png"),
        Identifier("textures/gui/widgets/widget_fluid_empty.png"),
        currentValue(),
        maxCapacity(),
    ) {
    override fun canResize(): Boolean = true

    override fun paint(
        matrices: PoseStack,
        x: Int,
        y: Int,
        mouseX: Int,
        mouseY: Int,
    ) {
        // Draw tank
        ScreenDrawing.texturedRect(matrices, x, y, this.getWidth(), this.getHeight(), this.bg, -1)

        // Draw fluid
        if (fluidVariant().isBlank) {
            return
        }

        val maxVal = if (this.max >= 0) this.properties.get(this.max) else this.maxValue
        var percent = this.properties.get(this.field).toFloat() / maxVal.toFloat()

        percent = 0.0f.coerceAtLeast(1.0f.coerceAtMost(percent))

        val barMax = this.height - 2
        percent = ((percent * barMax.toFloat()).toInt()).toFloat() / barMax.toFloat()
        val barSize = (barMax.toFloat() * percent).toInt()

        var top = y + this.getHeight()
        top -= barSize

        val sprite = FluidVariantRendering.getSprite(fluidVariant())!! // Fluid sprite cannot be null here since we check for blank above
        val fluidTexture = sprite.atlas().location()
        val color = FluidVariantRendering.getColor(fluidVariant())

        ScreenDrawing.texturedRect(
            matrices,
            x + 1,
            top - 1,
            this.width - 2,
            barSize,
            fluidTexture,
            sprite.u0,
            sprite.v0,
            sprite.u1,
            sprite.v1,
            color,
            0.6f,
        )
    }

    override fun addTooltip(information: TooltipBuilder) {
        val current = properties.get(currentValue())
        val max = properties.get(maxCapacity())
        val fluidName = StringHelper.toEnglishName(fluidVariant().fluid.registryName.path)

        information.add(TextComponent(fluidName).withStyle(ChatFormatting.DARK_AQUA))
        information.add(
            TranslatableComponent(Texts.CAPACITY)
                .withStyle(ChatFormatting.GOLD)
                .append(
                    TextComponent(" ${toReadableNumberString(max)} mB")
                        .withStyle(
                            ChatFormatting.GRAY,
                        ),
                ),
        )

        information.add(
            TranslatableComponent(Texts.STORED)
                .withStyle(ChatFormatting.GOLD)
                .append(
                    TextComponent(
                            " ${toReadableNumberString(current)} mB (${calculateFormattedPercentage(current.toDouble(), max.toDouble())}%)",
                        )
                        .withStyle(
                            ChatFormatting.GRAY,
                        ),
                ),
        )
    }
}
