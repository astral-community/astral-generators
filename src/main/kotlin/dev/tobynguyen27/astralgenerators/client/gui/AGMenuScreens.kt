package dev.tobynguyen27.astralgenerators.client.gui

import dev.tobynguyen27.astralgenerators.data.machines.assembler.AssemblerScreen
import dev.tobynguyen27.astralgenerators.gui.AGMenuTypes
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screens.MenuScreens

@Environment(EnvType.CLIENT)
object AGMenuScreens {
    fun init() {
        MenuScreens.register(AGMenuTypes.ASSEMBLER_MENU) { type, playerInventory, title -> AssemblerScreen(type, playerInventory, title) }
    }
}
