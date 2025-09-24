package dev.tobynguyen27.astralgenerators.data.machines.assembler

import dev.tobynguyen27.astralgenerators.data.recipes.AssemblerRecipe
import dev.tobynguyen27.astralgenerators.utils.AGInventory
import dev.tobynguyen27.astralgenerators.utils.MathHelper
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
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

class AssemblerEntity(
    type: BlockEntityType<AssemblerEntity>,
    pos: BlockPos,
    state: BlockState,
) : BlockEntity(type, pos, state), MenuProvider, PropertyDelegateHolder, ExtendedScreenHandlerFactory, AGInventory {
    companion object {
        // Energy
        private val MAX_ENERGY_CAPACITY: Long = MathHelper.tenPowerTo(5)
        private const val MAX_ENERGY_INSERT: Long = 1000.toLong()
        private const val MAX_ENERGY_EXTRACT: Long = 0.toLong()
        private const val ENERGY_STORAGE_TAG = "assembler_energy"

        // Fluid
        private const val MAX_FLUID_CAPACITY_IN_BUCKET = 10 * FluidConstants.BUCKET // 10 buckets
        private const val FLUID_STORAGE_AMOUNT_TAG = "assembler_fluid_amount"
        private const val FLUID_STORAGE_TYPE_TAG = "assembler_fluid_type"

        // Inventory
        const val CONTAINER_SIZE = 10

        // Data
        const val CONTAINER_DATA_SIZE = 6

        private var PROGRESS: Int = 0
        const val MAX_PROGRESS: Int = 100

        // Logic
        fun tick(level: Level, blockPos: BlockPos, blockState: BlockState, blockEntity: AssemblerEntity) {
            if (level.isClientSide) {
                // Client logic here
                return
            }

            if(hasRecipe(blockEntity)) {

            } else {
                blockEntity.resetProgress()
                updateActiveState(blockEntity, false)
            }
        }

        private fun hasRecipe(blockEntity: AssemblerEntity): Boolean {
            val level = blockEntity.level!!
            val container = object : AGInventory {
                override fun getItems(): NonNullList<ItemStack> {
                    return blockEntity.items
                }
            }

            val match = level.recipeManager.getRecipeFor(AssemblerRecipe.Type, container, level)

            //println(match)

          return true
        }

        private fun updateActiveState(entity: AssemblerEntity, active: Boolean) {
            val level = entity.level!!
            // Get the current block state
            val currentState = level.getBlockState(entity.blockPos)
            // Create a new state with the updated 'active' property
            val newState = currentState.setValue(Assembler.ACTIVE, active)
            // Update the block state in the world
            level.setBlock(entity.blockPos, newState, 3)

            entity.setChanged()
        }
    }

    override fun setChanged() {
        super<BlockEntity>.setChanged()
    }

    // Progress
    fun resetProgress() {
        PROGRESS = 0
    }

    // Storage
    private val items: NonNullList<ItemStack> =
        NonNullList.withSize(
            CONTAINER_SIZE,
            ItemStack.EMPTY) // Must be private cuz it won't generate getItems which will conflict with FlammeInventory.getItems()

    override fun getItems(): NonNullList<ItemStack> {
        return items
    }

    // Energy
    val energyStorage =
        object : SimpleEnergyStorage(MAX_ENERGY_CAPACITY, MAX_ENERGY_INSERT, MAX_ENERGY_EXTRACT) {
            override fun onFinalCommit() {
                setChanged()
                // This method is called when the energy storage is modified
            }
        }

    // Fluid
    val fluidStorage =
        object : SingleVariantStorage<FluidVariant>() {
            override fun getBlankVariant(): FluidVariant {
                return FluidVariant.blank()
            }

            override fun getCapacity(p0: FluidVariant): Long {
                return MAX_FLUID_CAPACITY_IN_BUCKET
            }

            override fun onFinalCommit() {
                setChanged()
            }
        }

    // Data
    val containerData =
        object : ContainerData {
            /** 0 is current energy 1 is max energy */
            override fun get(index: Int): Int {
                return when (index) {
                    0 -> energyStorage.amount.toInt()
                    1 -> energyStorage.capacity.toInt()
                    2 -> PROGRESS
                    3 -> MAX_PROGRESS
                    4 -> fluidStorage.amount.toInt()
                    5 -> MAX_FLUID_CAPACITY_IN_BUCKET.toInt()
                    else -> 0
                }
            }

            override fun set(index: Int, value: Int) {
                when (index) {
                    0 -> energyStorage.amount = value.toLong()
                    1 -> energyStorage.capacity = value.toLong()
                    2 -> PROGRESS = value
                }
            }

            override fun getCount(): Int {
                return CONTAINER_DATA_SIZE
            }
        }

    override fun getPropertyDelegate(): ContainerData {
        return containerData
    }

    override fun load(tag: CompoundTag) {
        // Load items
        ContainerHelper.loadAllItems(tag, items)

        // Load the energy storage amount from the NBT tag
        energyStorage.amount = tag.getLong(ENERGY_STORAGE_TAG)

        // Load the fluid storage amount and type from the NBT tag
        fluidStorage.amount = tag.getLong(FLUID_STORAGE_AMOUNT_TAG)
        fluidStorage.variant = FluidVariant.fromNbt(tag.getCompound(FLUID_STORAGE_TYPE_TAG))

        super.load(tag)
    }

    override fun saveAdditional(tag: CompoundTag) {
        // Save items
        ContainerHelper.saveAllItems(tag, items)

        // Save the energy storage amount to the NBT tag
        tag.putLong(ENERGY_STORAGE_TAG, energyStorage.amount)

        // Save the fluid storage amount and type to the NBT tag
        tag.putLong(FLUID_STORAGE_AMOUNT_TAG, fluidStorage.amount)
        tag.put(FLUID_STORAGE_TYPE_TAG, fluidStorage.variant.toNbt())

        super.saveAdditional(tag)
    }

    // Menu
    override fun getDisplayName(): Component {
        return TranslatableComponent(blockState.block.descriptionId)
    }

    override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
        return AssemblerMenu(i, inventory, ContainerLevelAccess.create(player.level, blockPos))
    }

    override fun writeScreenOpeningData(p0: ServerPlayer, p1: FriendlyByteBuf) {
        // Fluid variant
        p1.writeNbt(fluidStorage.variant.toNbt())
    }
}
