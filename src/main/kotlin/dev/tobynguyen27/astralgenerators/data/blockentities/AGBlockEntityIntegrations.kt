package dev.tobynguyen27.astralgenerators.data.blockentities

import dev.tobynguyen27.astralgenerators.data.AGBlockEntities.ASSEMBLER_ENTITY
import team.reborn.energy.api.EnergyStorage

object AGBlockEntitiesIntegrations {
    fun init() {
        EnergyStorage.SIDED.registerForBlockEntity({ blockEntity: AssemblerEntity, _ ->
            blockEntity.energyStorage
        }, ASSEMBLER_ENTITY.get())
    }
}