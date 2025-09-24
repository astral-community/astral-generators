package dev.tobynguyen27.astralgenerators.gui

import dev.tobynguyen27.astralgenerators.data.machines.assembler.AssemblerMenu
import dev.tobynguyen27.astralgenerators.utils.Identifier
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.core.Registry

object AGMenuTypes {
    val ASSEMBLER_MENU =
        Registry.register(
            Registry.MENU,
            Identifier("assembler_menu"),
            ExtendedScreenHandlerType { syncId, playerInventory, packet -> AssemblerMenu(syncId, playerInventory, packet) },
        )

    fun init() {}
}
