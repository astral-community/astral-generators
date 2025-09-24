package dev.tobynguyen27.astralgenerators.hook

object IntegrationHook {
    fun init() {
        EnergyAPI.init()
        FluidTransferAPI.init()
    }
}
