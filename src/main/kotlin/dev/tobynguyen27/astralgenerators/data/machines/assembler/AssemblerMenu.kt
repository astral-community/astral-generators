package dev.tobynguyen27.astralgenerators.data.machines.assembler

import dev.tobynguyen27.astralgenerators.gui.AGMenuTypes
import dev.tobynguyen27.astralgenerators.gui.widgets.EnergyBar
import dev.tobynguyen27.astralgenerators.gui.widgets.FluidBar
import dev.tobynguyen27.astralgenerators.utils.Identifier
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WBar
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class AssemblerMenu(
    syncId: Int,
    playerInventory: Inventory,
    ctx: ContainerLevelAccess,
) :
    SyncedGuiDescription(
        AGMenuTypes.ASSEMBLER_MENU,
        syncId,
        playerInventory,
        getBlockInventory(ctx, AssemblerEntity.CONTAINER_SIZE),
        getBlockPropertyDelegate(ctx, AssemblerEntity.CONTAINER_DATA_SIZE),
    ) {
    private var fluidVariant: FluidVariant = FluidVariant.blank()

    constructor(
        syncId: Int,
        playerInventory: Inventory,
        packet: FriendlyByteBuf
    ) : this(syncId, playerInventory, ContainerLevelAccess.NULL) {
        fluidVariant = FluidVariant.fromNbt(packet.readNbt())
    }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 15)

        // Item slots
        val inputSlots = WItemSlot(blockInventory, 0, 3, 3, false)
        root.add(inputSlots, 8, 3)

        val outputSlot = WItemSlot(blockInventory, 9, 1, 1, true)
        outputSlot.isInsertingAllowed = false
        root.add(outputSlot, 23, 6)

        // Energy bar
        val energyBar = EnergyBar({ 0 }, { 1 })
        root.add(
            energyBar,
            0,
            3,
            3,
            9,
        )

        // Fluid tank
        val fluidTank =
            FluidBar(
                { getFluidVariant() },
                { 4 },
                { 5 },
            )
        root.add(fluidTank, 4, 3, 3, 9)

        // Progress bar
        val progressBar =
            WBar(
                Identifier("textures/gui/widgets/widget_progress_empty.png"),
                Identifier("textures/gui/widgets/widget_progress_full.png"),
                2,
                3,
                WBar.Direction.RIGHT,
            )
        root.add(
            progressBar,
            18,
            6,
            3,
            3,
        )

        root.validate(this)
    }

    private fun getFluidVariant(): FluidVariant = fluidVariant
}
