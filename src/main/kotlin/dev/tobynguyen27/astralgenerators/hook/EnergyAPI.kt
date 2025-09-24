package dev.tobynguyen27.astralgenerators.hook

import dev.tobynguyen27.astralgenerators.data.AGBlockEntities.ASSEMBLER_ENTITY
import dev.tobynguyen27.astralgenerators.data.machines.assembler.AssemblerEntity
import team.reborn.energy.api.EnergyStorage

object EnergyAPI {
    fun init() {
        EnergyStorage.SIDED.registerForBlockEntity({ blockEntity: AssemblerEntity, _ -> blockEntity.energyStorage }, ASSEMBLER_ENTITY.get())
    }
}
