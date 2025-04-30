package dev.tobynguyen27.astralgenerators.data.blockentities

import dev.tobynguyen27.astralgenerators.data.blocks.machines.Assembler
import dev.tobynguyen27.astralgenerators.data.recipes.AssemblerRecipe
import dev.tobynguyen27.astralgenerators.gui.machines.AssemblerMenu
import dev.tobynguyen27.astralgenerators.utils.AGContainer
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler
import io.github.fabricators_of_create.porting_lib.util.LazyOptional
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.WorldlyContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import team.reborn.energy.api.base.SimpleEnergyStorage
import java.util.AbstractList
import java.util.stream.IntStream
import javax.swing.plaf.basic.BasicComboBoxUI
import kotlin.properties.PropertyDelegateProvider

class AssemblerEntity(type: BlockEntityType<AssemblerEntity>, pos: BlockPos, state: BlockState) :
    BlockEntity(type, pos, state), MenuProvider, AGContainer, PropertyDelegateHolder, WorldlyContainer {


    companion object {
        const val CONTAINER_SIZE = 10
        const val CONTAINER_DATA_SIZE = 4

        const val ENERGY_CAPACITY = 100000
        const val MAX_ENERGY_INPUT = 10000

        const val MAX_PROGRESS = 100

        fun tick(level: Level, pos: BlockPos, state: BlockState, entity: AssemblerEntity) {
            if (level.isClientSide) {
                // tick on client side
                return
            }
            // tick on server side

            // TODO: custom energy usage
            if (entity.energyStorage.amount < 1000) return

            if (hasRecipe(entity)) {
                updateActiveState(entity, true)
                entity.progress++
                //level.playSound(null, pos, AGSounds.ASSEMBLER, SoundSource.BLOCKS, 1f, 1f)
                if (entity.progress > MAX_PROGRESS) {
                    craftItem(entity)
                }
            } else {
                entity.resetProgress()
                updateActiveState(entity, false)
            }
        }

        fun hasRecipe(entity: AssemblerEntity): Boolean {
            val level = entity.level!!
            val container = object : AGContainer {
                override fun getItems(): NonNullList<ItemStack> {
                    return entity.items
                }
            }

            val match = level.recipeManager.getRecipeFor(AssemblerRecipe.Type.INSTANCE, container, level)

            return match.isPresent && canInsertItemIntoOutputSlot(
                container,
                match.get().output
            ) && canInsertAmountIntoOutputSlot(container)
        }


        private fun craftItem(entity: AssemblerEntity) {
            val level = entity.level!!
            val container = object : AGContainer {
                override fun getItems(): NonNullList<ItemStack> {
                    return entity.items
                }
            }

            val match = level.recipeManager.getRecipeFor(AssemblerRecipe.Type.INSTANCE, container, level)

            if (match.isPresent) {
                (0..8).forEach { slot ->
                    entity.removeItem(slot, 1)
                }

                entity.setItem(9, ItemStack(match.get().output.item, entity.getItem(9).count + 1))
                entity.energyStorage.amount -= 1000
                entity.resetProgress()
            }

        }

        private fun canInsertItemIntoOutputSlot(container: AGContainer, output: ItemStack): Boolean {
            return container.getItem(9).item == output.item || container.getItem(9).isEmpty
        }

        private fun canInsertAmountIntoOutputSlot(container: AGContainer): Boolean {
            return container.getItem(9).maxStackSize > container.getItem(9).count
        }

        private fun updateActiveState(entity: AssemblerEntity, active: Boolean) {
            val lvl = entity.level!!
            // Get the current block state
            val currentState = lvl.getBlockState(entity.blockPos)
            // Create a new state with the updated 'active' property
            val newState = currentState.setValue(Assembler.ACTIVE, active)
            // Update the block state in the world
            lvl.setBlock(entity.blockPos, newState, 3)
            entity.setChanged()
        }
    }

    private var progress = 0

    private val containerData = object : ContainerData {
        /**
         * 0 is current energy
         * 1 is max energy
         * 2 is current progress
         * 3 is max progress
         */
        override fun get(index: Int): Int {
            return when (index) {
                0 -> energyStorage.amount.toInt()
                1 -> energyStorage.capacity.toInt()
                2 -> progress
                3 -> MAX_PROGRESS
                else -> -1
            }
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> energyStorage.amount = value.toLong()
                1 -> energyStorage.capacity = value.toLong()
                2 -> progress = value
            }
        }

        override fun getCount(): Int {
            return CONTAINER_DATA_SIZE
        }
    }

    private val items: NonNullList<ItemStack> = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY)

    val energyStorage = object : SimpleEnergyStorage(ENERGY_CAPACITY.toLong(), MAX_ENERGY_INPUT.toLong(), 0) {
        override fun onFinalCommit() {
            setChanged()
        }
    }

    fun resetProgress() {
        progress = 0
    }

    override fun getSlotsForFace(side: Direction): IntArray {
        return if (side == Direction.UP) {
            IntArray(9) { it }
        } else {
            intArrayOf(9)
        }
    }

    override fun canPlaceItemThroughFace(index: Int, itemStack: ItemStack, direction: Direction?): Boolean {
        return direction == Direction.UP && index in 0..8
    }

    override fun canTakeItemThroughFace(index: Int, stack: ItemStack, direction: Direction): Boolean {
        return direction != Direction.UP && index == 9
    }

    override fun setChanged() {
        super<BlockEntity>.setChanged();
        level!!.sendBlockUpdated(blockPos, blockState, blockState, 3)
    }

    override fun getPropertyDelegate(): ContainerData {
        return containerData
    }

    override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
        return AssemblerMenu(i, inventory, ContainerLevelAccess.create(player.level, blockPos))
    }

    override fun getDisplayName(): Component {
        return TranslatableComponent(blockState.block.descriptionId)
    }

    override fun getItems(): NonNullList<ItemStack> {
        return items
    }

    override fun saveAdditional(tag: CompoundTag) {
        ContainerHelper.saveAllItems(tag, items)
        super.saveAdditional(tag)
        tag.putLong("assembler.energy", energyStorage.amount)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        ContainerHelper.loadAllItems(tag, items)
        energyStorage.amount = tag.getLong("assembler.energy")
    }
}