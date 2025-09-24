package dev.tobynguyen27.astralgenerators.data

import dev.tobynguyen27.astralgenerators.AstralGenerators
import net.minecraft.world.item.Item

object AGItems {
    val ASTRALNOMICON = AstralGenerators.REGISTRATE.item<Item>("astralnomicon", ::Item).register()

    fun init() {}
}
