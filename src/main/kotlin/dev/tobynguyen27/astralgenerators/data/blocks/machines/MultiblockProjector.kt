package dev.tobynguyen27.astralgenerators.data.blocks.machines

import dev.tobynguyen27.astralgenerators.data.AGBlockEntities
import dev.tobynguyen27.astralgenerators.data.blocks.machines.Assembler.Companion
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty

class MultiblockProjector(properties: Properties) : BaseEntityBlock(properties) {

    companion object {
        val ID = "multiblock_projector"

        val FACING = BlockStateProperties.HORIZONTAL_FACING
        val ACTIVE = BooleanProperty.create("active")
    }

    init {
        registerDefaultState(with(defaultBlockState()) { setValue(Assembler.FACING, Direction.NORTH) })
        registerDefaultState(with(defaultBlockState()) { setValue(Assembler.ACTIVE, false) })
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(Assembler.FACING, Assembler.ACTIVE)
    }

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        super.setPlacedBy(level, pos, state, placer, stack)

        if (placer !is Player) return
        level.setBlock(pos, level.getBlockState(pos).setValue(Assembler.FACING, placer.direction), 3)
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.MODEL
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.MULTIBLOCK_PROJECTOR_ENTITY.create(pos, state)
    }
}