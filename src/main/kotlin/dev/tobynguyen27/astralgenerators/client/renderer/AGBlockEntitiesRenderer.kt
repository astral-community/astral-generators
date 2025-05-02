package dev.tobynguyen27.astralgenerators.client.renderer

import dev.tobynguyen27.astralgenerators.data.AGBlockEntities
import dev.tobynguyen27.astralgenerators.data.blockentities.renderer.MultiblockProjectorEntityRenderer
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry

object AGBlockEntitiesRenderer {
    fun init() {
        BlockEntityRendererRegistry.register(
            AGBlockEntities.MULTIBLOCK_PROJECTOR_ENTITY.get(),
            ::MultiblockProjectorEntityRenderer
        )
    }
}