package dev.tobynguyen27.astralgenerators.gui.widgets

import dev.tobynguyen27.astralgenerators.data.lang.Texts
import dev.tobynguyen27.astralgenerators.utils.Identifier
import dev.tobynguyen27.astralgenerators.utils.MathHelper.calculateFormattedPercentage
import dev.tobynguyen27.astralgenerators.utils.StringHelper.toReadableNumberString
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WBar
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent

/**
 * @param currentValue The current energy amount (use PropertyDelegate index).
 * @param maxCapacity The maximum energy capacity (use PropertyDelegate index).
 */
class EnergyBar(
    val currentValue: () -> Int,
    val maxCapacity: () -> Int,
) :
    WBar(
        Identifier("textures/gui/widgets/widget_energy_empty.png"),
        Identifier("textures/gui/widgets/widget_energy_full.png"),
        currentValue(),
        maxCapacity(),
    ) {
    override fun addTooltip(information: TooltipBuilder) {
        val currentEnergy = properties.get(currentValue())
        val maxEnergyCapacity = properties.get(maxCapacity())

        information.add(TranslatableComponent(Texts.ENERGY).withStyle(ChatFormatting.DARK_AQUA))
        information.add(
            TranslatableComponent(Texts.CAPACITY)
                .withStyle(ChatFormatting.GOLD)
                .append(
                    TextComponent(" ${toReadableNumberString(maxEnergyCapacity)} E")
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
                            " ${toReadableNumberString(
            currentEnergy,
          )} E (${calculateFormattedPercentage(currentEnergy.toDouble(), maxEnergyCapacity.toDouble())}%)",
                        )
                        .withStyle(
                            ChatFormatting.GRAY,
                        ),
                ),
        )
    }
}
