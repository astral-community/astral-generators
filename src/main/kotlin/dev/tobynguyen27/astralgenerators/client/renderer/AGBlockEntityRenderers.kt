package dev.tobynguyen27.astralgenerators.client.renderer

import dev.tobynguyen27.astralgenerators.data.blockentities.AGBlockEntities
import dev.tobynguyen27.astralgenerators.data.blockentities.renderer.MultiblockProjectorEntityRenderer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry

@Environment(EnvType.CLIENT)
object AGBlockEntityRenderers {
    fun init() {
        BlockEntityRendererRegistry.register(
            AGBlockEntities.MULTIBLOCK_PROJECTOR_ENTITY.get(),
            ::MultiblockProjectorEntityRenderer
        )
    }
}