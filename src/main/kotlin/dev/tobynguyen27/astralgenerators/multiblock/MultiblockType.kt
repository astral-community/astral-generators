package dev.tobynguyen27.astralgenerators.multiblock

import net.minecraft.util.StringRepresentable

enum class MultiblockType(private val multiblockName: String) : StringRepresentable {
    STEAM_TURBINE("steam_turbine"),
    AMALGAMATION_MATRIX("amalgamation_matrix"),
    SOLID_BOILER("solid_boiler"),
    FLUID_BOILER("fluid_boiler");

    override fun getSerializedName(): String {
        return multiblockName
    }
}
