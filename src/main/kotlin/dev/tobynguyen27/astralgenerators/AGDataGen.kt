package dev.tobynguyen27.astralgenerators

import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraftforge.common.data.ExistingFileHelper

object AGDataGen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val helper = ExistingFileHelper.withResourcesFromArg()

        REGISTRATE.setupDatagen(fabricDataGenerator, helper)
    }
}
