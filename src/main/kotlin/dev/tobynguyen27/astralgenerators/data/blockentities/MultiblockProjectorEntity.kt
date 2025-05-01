package dev.tobynguyen27.astralgenerators.data.blockentities

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class MultiblockProjectorEntity(type: BlockEntityType<MultiblockProjectorEntity>, pos: BlockPos, state: BlockState) :
    BlockEntity(type, pos, state) {
}