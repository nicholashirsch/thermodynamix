package thermodynamix.core;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import thermodynamix.Thermodynamix;
import thermodynamix.machines.KilnBlock;
import thermodynamix.machines.KilnBlockEntity;

public class DeferredRegistry {

    // ----------------------
    //   BLOCK REGISTRATION
    // ----------------------
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Thermodynamix.MODID);

    public static final DeferredBlock<KilnBlock> KILN =
        BLOCKS.registerBlock("kiln", KilnBlock::new, props -> props.strength(3.5f).requiresCorrectToolForDrops());


    // -------------------------------
    //   BLOCK ENTITIES REGISTRATION
    // -------------------------------
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Thermodynamix.MODID);

    public static final Supplier<BlockEntityType<KilnBlockEntity>> KILN_BLOCK_ENTITY =
        BLOCK_ENTITIES.register("kiln", () -> new BlockEntityType<>(KilnBlockEntity::new, KILN.get()));


    // ---------------------
    //   ITEM REGISTRATION
    // ---------------------
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thermodynamix.MODID);

    public static final DeferredItem<BlockItem> KILN_ITEM = ITEMS.registerSimpleBlockItem(KILN);

    // --------------------------------
    //   DATA COMPONENTS REGISTRATION
    // --------------------------------
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Thermodynamix.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> TEMPERATURE =
        DATA_COMPONENTS.registerComponentType("temperature",
            builder -> builder.persistent(Codec.FLOAT).networkSynchronized((ByteBufCodecs.FLOAT)));

}
