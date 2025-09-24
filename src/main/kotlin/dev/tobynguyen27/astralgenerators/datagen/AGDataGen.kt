package dev.tobynguyen27.astralgenerators.datagen

import dev.tobynguyen27.astralgenerators.AstralGenerators
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraftforge.common.data.ExistingFileHelper

class AGDataGen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(p0: FabricDataGenerator) {
        val helper = ExistingFileHelper.withResourcesFromArg()

        AstralGenerators.REGISTRATE.setupDatagen(p0, helper)
    }
}
