package dev.tobynguyen27.astralgenerators.data

import dev.tobynguyen27.astralgenerators.data.blocks.AGCasings
import dev.tobynguyen27.astralgenerators.data.blocks.AGCoils
import dev.tobynguyen27.astralgenerators.data.blocks.AGGlasses
import dev.tobynguyen27.astralgenerators.data.blocks.AGMachines

object AGBlocks {
    fun init() {
        AGMachines.init()
        AGCasings.init()
        AGGlasses.init()
        AGCoils.init()
    }
}