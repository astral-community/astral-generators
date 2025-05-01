package dev.tobynguyen27.astralgenerators.client.menu

import dev.tobynguyen27.astralgenerators.gui.AGMenuTypes
import dev.tobynguyen27.astralgenerators.gui.machines.AssemblerScreen
import net.minecraft.client.gui.screens.MenuScreens

object AGMenuScreens {
    fun init() {
        MenuScreens.register(AGMenuTypes.ASSEMBLER_MENU) { type, playerInventory, title ->
            AssemblerScreen(type, playerInventory, title)
        }
    }
}