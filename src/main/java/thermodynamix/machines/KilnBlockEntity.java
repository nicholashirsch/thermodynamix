package thermodynamix.machines;

import com.lowdragmc.lowdraglib2.gui.factory.BlockUIMenuType;
import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;
import com.lowdragmc.lowdraglib2.gui.ui.UI;
import com.lowdragmc.lowdraglib2.gui.ui.UIElement;
import com.lowdragmc.lowdraglib2.gui.ui.elements.ItemSlot;
import com.lowdragmc.lowdraglib2.gui.ui.elements.Label;
import com.lowdragmc.lowdraglib2.gui.ui.elements.inventory.InventorySlots;
import com.lowdragmc.lowdraglib2.gui.ui.style.StylesheetManager;
import com.lowdragmc.lowdraglib2.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib2.syncdata.holder.blockentity.ISyncPersistRPCBlockEntity;
import com.lowdragmc.lowdraglib2.syncdata.storage.FieldManagedStorage;

import dev.vfyjxf.taffy.style.FlexDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import thermodynamix.core.DeferredRegistry;

/**
 * Block entity for the Kiln block.
 * 
 * NOTE: This class is overly commented as a template for making other block entities in the future.
 */
public class KilnBlockEntity extends BlockEntity implements ISyncPersistRPCBlockEntity {

    /**
     * Let's the LDLib ISyncPersistRPCBlockEntity which handles saving and loading BlockEntity information to the disk
     * of the machine hosting the server automatically. When this class is instantiated it scans for fields annotated
     * with @Persisted and flags them for automatic sync with the disk.
     */
    private final FieldManagedStorage syncStorage = new FieldManagedStorage(this);

    /**
     * Standard block entity constructor. This is called when the registry creates this block entity.
     * </p>
     * For an example of this in action @see KilnBlock#newBlockEntity(BlockPos, BlockState).
     * 
     * @param position The position of the block in the world, required param.
     * @param state    The current BlockState of the block, required param.
     */
    public KilnBlockEntity(BlockPos position, BlockState state) {
        super(DeferredRegistry.KILN_BLOCK_ENTITY.get(), position, state);
    }

    // 0 = input, 1 = fuel, 2 = output
    @Persisted //
    private final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(3) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
        }
    };

    /**
     * Called when saving to/loading from the disk. Takes data in the FieldManagedStorage instance and syncs it with the
     * disk.
     *
     * @return The FieldManagedStorage instance containing fields annotated with @Persisted.
     */
    @Override
    public FieldManagedStorage getSyncStorage() {
        return syncStorage;
    }

    public ModularUI createUI(BlockUIMenuType.BlockUIHolder holder) {
        var root = new UIElement().addChildren(
                new Label().setText("Kiln"),
                new UIElement().addChildren(
                        new ItemSlot().bind(inventory, 0),
                        new ItemSlot().bind(inventory, 1),
                        new ItemSlot().bind(inventory, 2)).layout(l -> l.gapAll(2).flexDirection(FlexDirection.ROW)),
                new InventorySlots()).addClass("panel_bg");
        return new ModularUI(UI.of(root, StylesheetManager.INSTANCE.getStylesheetSafe(StylesheetManager.MC)),
                holder.player);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        Containers.dropContents(level, pos, inventory.copyToList());
    }
}
