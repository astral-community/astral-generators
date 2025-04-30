package dev.tobynguyen27.astralgenerators.client

import dev.tobynguyen27.astralgenerators.gui.AGMenuTypes
import dev.tobynguyen27.astralgenerators.gui.machines.AssemblerScreen
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.gui.screens.MenuScreens

object AGClient : ClientModInitializer {
    override fun onInitializeClient() {
        MenuScreens.register(AGMenuTypes.ASSEMBLER_MENU) { type, playerInventory, title ->
            AssemblerScreen(type, playerInventory, title)
        }
    }
}