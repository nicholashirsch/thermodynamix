package thermodynamix.machines.kiln;

import com.lowdragmc.lowdraglib2.gui.factory.BlockUIMenuType;
import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;
import com.lowdragmc.lowdraglib2.gui.ui.UI;
import com.lowdragmc.lowdraglib2.gui.ui.UIElement;
import com.lowdragmc.lowdraglib2.gui.ui.elements.ItemSlot;
import com.lowdragmc.lowdraglib2.gui.ui.elements.Label;
import com.lowdragmc.lowdraglib2.gui.ui.elements.inventory.InventorySlots;
import com.lowdragmc.lowdraglib2.gui.ui.style.StylesheetManager;

import dev.vfyjxf.taffy.style.FlexDirection;

public class KilnGui extends ModularUI {
    public KilnGui(BlockUIMenuType.BlockUIHolder holder, KilnBlockEntity.KilnInventory inventory) {
        var root = new UIElement().addChildren(
                new Label().setText("Kiln"),
                new UIElement().addChildren(
                        new ItemSlot().bind(inventory, 0),
                        new ItemSlot().bind(inventory, 1),
                        new ItemSlot().bind(inventory, 2)).layout(l -> l.gapAll(2).flexDirection(FlexDirection.ROW)),
                new InventorySlots()).addClass("panel_bg");
        super(UI.of(root, StylesheetManager.INSTANCE.getStylesheetSafe(StylesheetManager.MC)),
                holder.player);
    }
}
