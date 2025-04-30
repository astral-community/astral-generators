package dev.tobynguyen27.astralgenerators.data

import dev.tobynguyen27.astralgenerators.data.blockentities.AGBlockEntities.ASSEMBLER_ENTITY
import dev.tobynguyen27.astralgenerators.data.blockentities.AssemblerEntity
import dev.tobynguyen27.astralgenerators.data.blocks.AGMachines
import team.reborn.energy.api.EnergyStorage

object AGBlocks {
    fun init() {
        AGMachines.init()
    }
}