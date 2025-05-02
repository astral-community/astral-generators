package dev.tobynguyen27.astralgenerators.data.tags

import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.Fluid

object FluidTags {
    val STEAM_TAG: TagKey<Fluid> =
        TagKey.create(
            ResourceKey.createRegistryKey(ResourceLocation("c", "steam")),
            ResourceLocation("c", "steam")
        )
}