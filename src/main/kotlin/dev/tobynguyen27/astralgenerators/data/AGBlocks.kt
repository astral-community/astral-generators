package dev.tobynguyen27.astralgenerators.data

import dev.tobynguyen27.astralgenerators.data.machines.assembler.Assembler
import dev.tobynguyen27.astralgenerators.data.machines.assembler.AssemblerEntity
import dev.tobynguyen27.astralgenerators.data.registries.MachineRegistry

object AGBlocks {
    val ASSEMBLER =
        MachineRegistry.registerSimpleMachine<Assembler>(Assembler.ID, ::Assembler)
            .blockEntity { type, pos, state -> AssemblerEntity(type, pos, state) }
            .build()
            .register()

    fun init() {}
}
