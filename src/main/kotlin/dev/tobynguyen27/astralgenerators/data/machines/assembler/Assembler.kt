package dev.tobynguyen27.astralgenerators.data.machines.assembler

import dev.tobynguyen27.astralgenerators.data.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult

class Assembler(
    properties: Properties,
) : BaseEntityBlock(properties) {
    companion object {
        const val ID = "assembler"
        val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
        val ACTIVE: BooleanProperty = BooleanProperty.create("active")
    }

    init {
        registerDefaultState(with(defaultBlockState()) { setValue(FACING, Direction.NORTH) })
        registerDefaultState(with(defaultBlockState()) { setValue(ACTIVE, false) })
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(FACING, ACTIVE)
    }

    override fun setPlacedBy(
        level: Level,
        pos: BlockPos,
        state: BlockState,
        placer: LivingEntity?,
        stack: ItemStack,
    ) {
        if (placer !is Player) return

        level.setBlock(pos, level.getBlockState(pos).setValue(FACING, placer.direction.opposite), 3)
    }

    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult,
    ): InteractionResult {
        if (!level.isClientSide) {
            player.openMenu(state.getMenuProvider(level, pos))
        }

        return InteractionResult.SUCCESS
    }

    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL

    override fun newBlockEntity(
        pos: BlockPos,
        state: BlockState,
    ): BlockEntity = AGBlockEntities.ASSEMBLER_ENTITY.create(pos, state)

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T?>
    ): BlockEntityTicker<T?>? {
        return createTickerHelper(blockEntityType, AGBlockEntities.ASSEMBLER_ENTITY.get(), AssemblerEntity::tick)
    }
}
