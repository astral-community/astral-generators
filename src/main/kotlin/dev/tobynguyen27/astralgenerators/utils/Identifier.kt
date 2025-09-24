package dev.tobynguyen27.astralgenerators.utils

import dev.tobynguyen27.astralgenerators.AstralGenerators
import net.minecraft.resources.ResourceLocation

class Identifier(
    path: String,
) : ResourceLocation(AstralGenerators.MOD_ID, path)
