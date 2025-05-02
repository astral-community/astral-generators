package dev.tobynguyen27.astralgenerators.data.blockentities.renderer

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.data.blockentities.MultiblockProjectorEntity
import dev.tobynguyen27.astralgenerators.data.blocks.machines.MultiblockProjector
import dev.tobynguyen27.astralgenerators.multiblock.MultiblockManager
import dev.tobynguyen27.astralgenerators.multiblock.MultiblockRenderers
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.resources.ResourceLocation

class MultiblockProjectorEntityRenderer(ctx: BlockEntityRendererProvider.Context) :
    BlockEntityRenderer<MultiblockProjectorEntity> {
    override fun render(
        blockEntity: MultiblockProjectorEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        if (!blockEntity.blockState.getValue(MultiblockProjector.ACTIVE)) return
        val multiblockType =
            blockEntity.blockState.getValue(MultiblockProjector.MULTIBLOCK_TYPE).serializedName
        val multiblockDefinition =
            MultiblockManager.getDefinition(
                ResourceLocation(AstralGenerators.MOD_ID, multiblockType)
            )!!

        MultiblockRenderers.renderBuildGuide(
            blockEntity.level!!,
            blockEntity.blockPos,
            poseStack,
            bufferSource,
            multiblockDefinition,
            200,
            partialTick,
            blockEntity.blockState.getValue(MultiblockProjector.FACING)
        )
    }
}