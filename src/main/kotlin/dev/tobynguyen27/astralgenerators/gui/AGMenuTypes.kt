package dev.tobynguyen27.astralgenerators.gui

import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import dev.tobynguyen27.astralgenerators.gui.machines.AssemblerMenu
import net.minecraft.core.Registry
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.MenuType

object AGMenuTypes {
    val ASSEMBLER_MENU =
        Registry.register(
            Registry.MENU,
            AstralGenerators.id("assembler_menu"),
            MenuType { id, inv -> AssemblerMenu(id, inv, ContainerLevelAccess.NULL) }
        )

    fun init() {
        LOGGER.info("Registering menu...")
    }
}