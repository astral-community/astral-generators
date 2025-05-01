package dev.tobynguyen27.astralgenerators.multiblock

import net.minecraft.core.BlockPos

@JvmRecord data class InvalidPart(val pos: BlockPos, val expected: MultiblockPart)