package dev.tobynguyen27.astralgenerators.client

import dev.tobynguyen27.astralgenerators.client.menu.AGMenuScreens
import dev.tobynguyen27.astralgenerators.client.network.MultiblockDefinitionPacketHandler
import dev.tobynguyen27.astralgenerators.client.renderer.AGBlockEntitiesRenderer
import dev.tobynguyen27.astralgenerators.gui.AGMenuTypes
import dev.tobynguyen27.astralgenerators.gui.machines.AssemblerScreen
import dev.tobynguyen27.astralgenerators.utils.TimeKeeper
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.gui.screens.MenuScreens

object AGClient : ClientModInitializer {
    override fun onInitializeClient() {
        AGMenuScreens.init()
        AGBlockEntitiesRenderer.init()
        MultiblockDefinitionPacketHandler.init()

        ClientTickEvents.END_CLIENT_TICK.register { TimeKeeper.incrementClientTick() }
    }
}