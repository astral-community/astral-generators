package dev.tobynguyen27.astralgenerators.data.items

import com.tterrag.registrate.util.entry.ItemEntry
import dev.tobynguyen27.astralgenerators.AstralGenerators.LOGGER
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE

object AGItems {
    val ASTRALNOMICON: ItemEntry<Astralnomicon> =
        REGISTRATE.item<Astralnomicon>(Astralnomicon.ID, ::Astralnomicon).register()

    fun init() {
        LOGGER.info("Registering items...")
    }
}