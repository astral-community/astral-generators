package dev.tobynguyen27.astralgenerators.hook

import dev.tobynguyen27.astralgenerators.data.AGBlockEntities
import dev.tobynguyen27.astralgenerators.data.machines.assembler.AssemblerEntity
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage

object FluidTransferAPI {
    fun init() {
        FluidStorage.SIDED.registerForBlockEntity<AssemblerEntity>(
            { blockEntity, direction -> blockEntity.fluidStorage },
            AGBlockEntities.ASSEMBLER_ENTITY.get(),
        )
    }
}
