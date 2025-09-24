package dev.tobynguyen27.astralgenerators.data

import com.tterrag.registrate.util.entry.BlockEntityEntry
import dev.tobynguyen27.astralgenerators.data.machines.assembler.AssemblerEntity
import net.minecraft.core.Registry

object AGBlockEntities {
    val ASSEMBLER_ENTITY: BlockEntityEntry<AssemblerEntity> =
        BlockEntityEntry.cast<AssemblerEntity>(
            AGBlocks.ASSEMBLER.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY),
        )

    fun init() {}
}
