package dev.tobynguyen27.astralgenerators.data.blockentities

import com.tterrag.registrate.util.entry.BlockEntityEntry
import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import dev.tobynguyen27.astralgenerators.data.blocks.AGMachines
import net.minecraft.core.Registry
import team.reborn.energy.api.EnergyStorage

object AGBlockEntities {

    val ASSEMBLER_ENTITY =
        BlockEntityEntry.cast<AssemblerEntity>(AGMachines.ASSSEMBLER.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY))

    fun init() {
        LOGGER.info("Registering block entities...")
    }
}
