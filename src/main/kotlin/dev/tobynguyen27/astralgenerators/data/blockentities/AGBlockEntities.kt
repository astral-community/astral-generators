package dev.tobynguyen27.astralgenerators.data.blockentities

import com.tterrag.registrate.util.entry.BlockEntityEntry
import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import dev.tobynguyen27.astralgenerators.data.blocks.AGMachines
import net.minecraft.core.Registry

object AGBlockEntities {

    val ASSEMBLER_ENTITY =
        BlockEntityEntry.cast<AssemblerEntity>(AGMachines.ASSSEMBLER.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY))
    val MULTIBLOCK_PROJECTOR_ENTITY =
        BlockEntityEntry.cast<MultiblockProjectorEntity>(AGMachines.MULTIBLOCK_PROJECTOR.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY))

    fun init() {
        LOGGER.info("Registering block entities...")
    }
}
