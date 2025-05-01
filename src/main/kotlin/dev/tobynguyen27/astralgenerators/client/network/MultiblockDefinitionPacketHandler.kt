package dev.tobynguyen27.astralgenerators.client.network

import com.google.gson.JsonParser
import dev.tobynguyen27.astralgenerators.multiblock.MultiblockDefinition
import dev.tobynguyen27.astralgenerators.multiblock.MultiblockManager
import dev.tobynguyen27.astralgenerators.network.ChannelList
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.resources.ResourceLocation

@Environment(EnvType.CLIENT)
object MultiblockDefinitionPacketHandler {
    fun init() {
        ClientPlayNetworking.registerGlobalReceiver(ChannelList.MULTIBLOCK_CHANNEL) { client,
                                                                                      _,
                                                                                      buf,
                                                                                      _ ->
            val count = buf.readVarInt()
            val multiBlockMap = hashMapOf<ResourceLocation, MultiblockDefinition>()

            for (i in 0 until count) {
                val id = buf.readResourceLocation()
                val json = buf.readUtf()
                multiBlockMap[id] = MultiblockDefinition(id, JsonParser.parseString(json))
            }

            client.execute {
                multiBlockMap.forEach { (key, value) ->
                    MultiblockManager.LOCAL_DEFINITIONS[key] = value
                }
            }
        }
    }
}