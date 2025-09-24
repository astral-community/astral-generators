package dev.tobynguyen27.astralgenerators.client

import dev.tobynguyen27.astralgenerators.client.gui.AGMenuScreens
import net.fabricmc.api.ClientModInitializer

class AGClient : ClientModInitializer {
    override fun onInitializeClient() {
        AGMenuScreens.init()
    }
}
